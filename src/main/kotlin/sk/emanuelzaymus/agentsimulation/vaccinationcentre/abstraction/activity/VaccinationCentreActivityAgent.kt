package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.countLastStats

/**
 * Represents activity for vaccination centre with workers and waiting queue.
 */
abstract class VaccinationCentreActivityAgent<T : VaccinationCentreWorker>(
    id: Int, mySim: Simulation, parent: Agent?, numberOfWorkers: Int, init: (Int) -> T
) : VaccinationCentreAgent(id, mySim, parent), IStatisticsAgent<T> {

    protected abstract val statsName: String

    val queue = SimQueue<Message>(WStat(mySim))
    val waitingTimeStat = Stat()
    val workers = BusyList(numberOfWorkers, init)

    override fun prepareReplication() {
        super.prepareReplication()
        queue.clear()
        waitingTimeStat.clear()
        workers.restart()
    }

    override fun countLastStats() {
        queue.countLastStats()
        workers.forEach { it.countLastStats() }
    }

    override val actualQueueLength get() = queue.size

    override val averageQueueLength get() = queue.lengthStatistic().mean()

    override val averageWaitingTime get() = waitingTimeStat.mean()

    override val busyWorkersCount get() = workers.count { it.isBusy }

    override val averageWorkload get() = workers.map { it.workloadStat.mean() }.average()

    override fun <R> convertWorkers(transform: (T) -> R): Iterable<R> = workers.map(transform)

    override fun checkFinalState() {
        if (queue.isNotEmpty()) {
            throw IllegalStateException("VaccinationCentreActivityAgent - there are still waiting messages in the queue.")
        }
        workers.checkFinalState()
    }

    open fun printStats() = println(
        """
        $statsName
        Queue length mean: $averageQueueLength
        Waiting time mean: $averageWaitingTime
        Workload mean: $averageWorkload
        """.trimIndent()
    )

}