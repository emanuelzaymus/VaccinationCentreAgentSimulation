package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.utils.DEBUG_MODE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.ExaminationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.RegistrationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.VaccinationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting.WaitingAgent

class VaccinationCentreAgentSimulation(
    numberOfPatients: Int,
    numberOfAdminWorkers: Int,
    numberOfDoctors: Int,
    numberOfNurses: Int
) : Simulation() {

    private val modelAgent = ModelAgent(this)
    private val environmentAgent = EnvironmentAgent(this, modelAgent, numberOfPatients)
    private val registrationAgent = RegistrationAgent(this, modelAgent, numberOfAdminWorkers)
    private val examinationAgent = ExaminationAgent(this, modelAgent, numberOfDoctors)
    private val vaccinationAgent = VaccinationAgent(this, modelAgent, numberOfNurses)
    private val waitingAgent = WaitingAgent(this, modelAgent)

    private val registrationStats = AgentStats()
    private val examinationStats = AgentStats()
    private val vaccinationStats = AgentStats()
    private val waitingStats = Stat()

    override fun prepareSimulation() {
        super.prepareSimulation()

        registrationStats.clear()
        examinationStats.clear()
        vaccinationStats.clear()
        waitingStats.clear()
    }

    override fun prepareReplication() {
        super.prepareReplication()
        modelAgent.runSimulation()
    }

    override fun replicationFinished() {
        super.replicationFinished()

        registrationStats.addStatistics(registrationAgent)
        examinationStats.addStatistics(examinationAgent)
        vaccinationStats.addStatistics(vaccinationAgent)
        waitingStats.addSample(waitingAgent.getWaitingPatientsCountMean())

        if (DEBUG_MODE) {
            registrationAgent.printStats()
            examinationAgent.printStats()
            vaccinationAgent.printStats()
            waitingAgent.printStats()
        }

        checkFinalState()
    }

    override fun simulationFinished() {
        super.simulationFinished()

        println("-----------------")
        registrationStats.print("Registration")
        examinationStats.print("Examination")
        vaccinationStats.print("Vaccination")
    }

    private fun checkFinalState() {
        environmentAgent.checkFinalState()
        registrationAgent.checkFinalState()
        examinationAgent.checkFinalState()
        vaccinationAgent.checkFinalState()
        waitingAgent.checkFinalState()
    }

}