package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ExaminationAgent(mySim: Simulation, parent: Agent, numberOfDoctors: Int) :
    Agent(Ids.examinationAgent, mySim, parent), IReusable {

    val queue = SimQueue<Message>(WStat(mySim()))
    val waitingTimeStat = Stat()
    val doctors = BusyList(numberOfDoctors) { Doctor(WStat(mySim)) }

    init {
        ExaminationManager(mySim, this)
        ExaminationProcess(mySim, this)

        addOwnMessage(MessageCodes.examination)
        addOwnMessage(MessageCodes.examinationDone)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        queue.clear()
        waitingTimeStat.clear()
        doctors.restart()
    }

    fun queueLengthStat(): WStat = queue.lengthStatistic()

    fun doctorsWorkloadMean(): Double = doctors.map { it.workloadStat.mean() }.average()

    override fun checkFinalState() {
        if (queue.isNotEmpty()) {
            throw IllegalStateException("RegistrationAgent - there are still waiting messages in patientQueue.")
        }
        doctors.checkFinalState()
    }

    override fun restart() {}

}