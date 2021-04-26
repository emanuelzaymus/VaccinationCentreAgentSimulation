package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.ExaminationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.RegistrationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.VaccinationAgent

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

    private val registrationStats = AgentStats()
    private val examinationStats = AgentStats()
    private val vaccinationStats = AgentStats()

    override fun prepareSimulation() {
        super.prepareSimulation()

        registrationStats.clear()
        examinationStats.clear()
        vaccinationStats.clear()
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

//        registrationAgent.printStats()
//        examinationAgent.printStats()
//        vaccinationAgent.printStats()

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
    }

}