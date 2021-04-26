package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message

open class VaccinationCentreActivityAgent<T : VaccinationCentreWorker>(
    id: Int, mySim: Simulation, parent: Agent?, numberOfWorkers: Int, init: (Int) -> T
) : VaccinationCentreAgent(id, mySim, parent) {

    val queue = SimQueue<Message>(WStat(mySim()))
    val waitingTimeStat = Stat()
    val workers = BusyList(numberOfWorkers, init)

    override fun prepareReplication() {
        super.prepareReplication()
        queue.clear()
        waitingTimeStat.clear()
        workers.restart()
    }

    fun queueLengthStat(): WStat = queue.lengthStatistic()

    fun workersWorkloadMean(): Double = workers.map { it.workloadStat.mean() }.average()

    override fun checkFinalState() {
        if (queue.isNotEmpty()) {
            throw IllegalStateException("VaccinationCentreActivityAgent - there are still waiting messages in the queue.")
        }
        workers.checkFinalState()
    }

}