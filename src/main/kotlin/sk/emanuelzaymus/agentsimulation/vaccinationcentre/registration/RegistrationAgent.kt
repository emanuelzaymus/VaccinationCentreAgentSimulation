package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class RegistrationAgent(id: Int = Ids.registrationAgent, mySim: Simulation, parent: Agent) : Agent(id, mySim, parent) {

    lateinit var patientQueue: SimQueue<MessageForm>
    lateinit var waitingTimeStat: Stat
    var isWorking = false

    init {
        RegistrationManager(mySim = mySim, myAgent = this)
        RegistrationProcess(mySim = mySim, myAgent = this)

        addOwnMessage(MessageCodes.patientRegistration)
        addOwnMessage(MessageCodes.registrationEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        patientQueue = SimQueue(WStat(mySim()))
        waitingTimeStat = Stat()
        isWorking = false
    }

    fun queueLengthStat(): WStat = patientQueue.lengthStatistic()

}