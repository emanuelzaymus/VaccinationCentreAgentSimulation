package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.PatientMessage

class RegistrationAgent(mySim: Simulation, parent: Agent) : Agent(Ids.registrationAgent, mySim, parent), IReusable {

    lateinit var patientQueue: SimQueue<PatientMessage>
    lateinit var waitingTimeStat: Stat

    private val registrationManager = RegistrationManager(mySim, this)

    init {
        RegistrationProcess(mySim, this)

        addOwnMessage(MessageCodes.patientRegistration)
        addOwnMessage(MessageCodes.registrationEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        patientQueue = SimQueue(WStat(mySim()))
        waitingTimeStat = Stat()

        registrationManager.restart()
    }

    fun queueLengthStat(): WStat = patientQueue.lengthStatistic()

    override fun checkFinalState() {
        if (patientQueue.isNotEmpty()) {
            throw IllegalStateException("RegistrationAgent - there are still waiting messages in patientQueue.")
        }
        registrationManager.checkFinalState()
    }

    override fun restart() {
        TODO("Not yet implemented")
    }

}