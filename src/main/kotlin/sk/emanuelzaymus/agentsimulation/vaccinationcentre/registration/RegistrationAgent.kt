package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message

class RegistrationAgent(mySim: Simulation, parent: Agent, numberOfAdminWorkers: Int) :
    Agent(Ids.registrationAgent, mySim, parent), IReusable {

    lateinit var messageQueue: SimQueue<Message>
    lateinit var waitingTimeStat: Stat
    val workers = BusyList(numberOfAdminWorkers) { AdministrativeWorker() }

    init {
        RegistrationManager(mySim, this)
        RegistrationProcess(mySim, this)

        addOwnMessage(MessageCodes.patientRegistration)
        addOwnMessage(MessageCodes.registrationEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        messageQueue = SimQueue(WStat(mySim()))
        waitingTimeStat = Stat()
    }

    fun queueLengthStat(): WStat = messageQueue.lengthStatistic()

    override fun checkFinalState() {
        if (messageQueue.isNotEmpty()) {
            throw IllegalStateException("RegistrationAgent - there are still waiting messages in patientQueue.")
        }
        workers.checkFinalState()
    }

    override fun restart() {
        workers.restart()
    }

}