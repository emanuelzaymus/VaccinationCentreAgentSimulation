package sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting

import OSPABA.Agent
import OSPABA.Simulation
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.ICountRoomAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class WaitingAgent(mySim: Simulation, parent: Agent) : VaccinationCentreAgent(Ids.waitingAgent, mySim, parent),
    ICountRoomAgent {

    private val patientCountStats = WStat(mySim)
    private var waitingPatients = 0

    init {
        WaitingManager(mySim, this)
        WaitingProcess(mySim, this)

        addOwnMessage(MessageCodes.waitingStart)
        addOwnMessage(MessageCodes.waitingEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()

        patientCountStats.clear()
        waitingPatients = 0
    }

    override fun countLastStats() = addSampleToPatientCountStats()

    fun incrementWaitingPatients() {
        waitingPatients++
        addSampleToPatientCountStats()
    }

    fun decrementWaitingPatients() {
        waitingPatients--
        addSampleToPatientCountStats()
    }

    private fun addSampleToPatientCountStats() = patientCountStats.addSample(waitingPatients.toDouble())

    override val actualCount get() = waitingPatients

    override val averageCount get() = patientCountStats.mean()

    fun printStats() = println(
        """
        Waiting
        Patient count mean: $averageCount
        """.trimIndent()
    )

    override fun checkFinalState() {
        if (waitingPatients > 0)
            throw IllegalStateException("There are still waiting $waitingPatients patients in the waiting room.")
    }

}