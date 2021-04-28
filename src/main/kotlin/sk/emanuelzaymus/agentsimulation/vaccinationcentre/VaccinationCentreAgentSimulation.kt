package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.ExaminationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.RegistrationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.VaccinationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsAgent
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
    private val injectionsAgent = InjectionsAgent(this, vaccinationAgent, 2)

    private val registrationStats = AgentStats()
    private val examinationStats = AgentStats()
    private val vaccinationStats = AgentStats()
    private val waitingStats = Stat()
    private val nursesQueueLengthStats = Stat()

    override fun prepareSimulation() {
        super.prepareSimulation()

        registrationStats.clear()
        examinationStats.clear()
        vaccinationStats.clear()
        waitingStats.clear()
        nursesQueueLengthStats.clear()
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
        nursesQueueLengthStats.addSample(injectionsAgent.queueLengthMean())

        println("Repl: " + currentReplication())
        if (PRINT_REPL_STATS) {
            registrationAgent.printStats()
            examinationAgent.printStats()
            vaccinationAgent.printStats()
            waitingAgent.printStats()
            injectionsAgent.printStats()
            println()
        }

        checkFinalState()
    }

    override fun simulationFinished() {
        super.simulationFinished()

        println("-----------------")
        registrationStats.print("Registration")
        examinationStats.print("Examination")
        vaccinationStats.print("Vaccination")
        println("Waiting\nPatients count mean: " + waitingStats.mean())
        println("Injections Preparation\nNurses q length mean: " + waitingStats.mean())
    }

    private fun checkFinalState() {
        environmentAgent.checkFinalState()
        registrationAgent.checkFinalState()
        examinationAgent.checkFinalState()
        vaccinationAgent.checkFinalState()
        waitingAgent.checkFinalState()
        injectionsAgent.checkFinalState()
    }

}