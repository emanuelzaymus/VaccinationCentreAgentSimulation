package sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting

import OSPABA.Agent
import OSPABA.Simulation
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class WaitingAgent(mySim: Simulation, parent: Agent) : VaccinationCentreAgent(Ids.waitingAgent, mySim, parent) {

    private val patientCountStats = WStat(mySim)

    var waitingPatients = 0
        private set

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

    fun incrementWaitingPatients() {
        waitingPatients++
        patientCountStats.addSample(waitingPatients.toDouble())
    }

    fun decrementWaitingPatients() {
        waitingPatients--
        patientCountStats.addSample(waitingPatients.toDouble())
    }

    fun getWaitingPatientsCountMean(): Double = patientCountStats.mean()

    fun printStats() = println(
        """
        Waiting
        Patient count mean: ${getWaitingPatientsCountMean()}
        """.trimIndent()
    )

    override fun checkFinalState() {
        if (waitingPatients > 0)
            throw IllegalStateException("There are still waiting $waitingPatients patients in the waiting room.")
    }

}