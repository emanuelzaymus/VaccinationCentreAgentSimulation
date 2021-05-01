package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

abstract class VaccinationCentreActivityAgent<T : VaccinationCentreWorker>(
    id: Int, mySim: Simulation, parent: Agent?, numberOfWorkers: Int, init: (Int) -> T
) : VaccinationCentreAgent(id, mySim, parent), IStatisticsAgent {

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

    override fun queueLengthMean(): Double = queue.lengthStatistic().mean()

    override fun waitingTimeMean(): Double = waitingTimeStat.mean()

    override fun workersWorkloadMean(): Double = workers.map { it.workloadStat.mean() }.average()

    override fun checkFinalState() {
        if (queue.isNotEmpty()) {
            throw IllegalStateException("VaccinationCentreActivityAgent - there are still waiting messages in the queue.")
        }
        workers.checkFinalState()
    }

    open fun printStats() = println(
        """
        $statsName
        Queue length mean: ${queueLengthMean()}
        Waiting time mean: ${waitingTimeMean()}
        Workload mean: ${workersWorkloadMean()}
        """.trimIndent()
    )

}