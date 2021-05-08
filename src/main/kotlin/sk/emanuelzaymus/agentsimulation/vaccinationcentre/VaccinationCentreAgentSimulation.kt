package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.ExaminationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak.LunchBreakAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.RegistrationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.stats.AgentStats
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.stats.OverallStats
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.transfer.TransferAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.VaccinationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting.WaitingAgent

class VaccinationCentreAgentSimulation(
    numberOfPatients: Int,
    numberOfAdminWorkers: Int,
    numberOfDoctors: Int,
    numberOfNurses: Int,
    earlyArrivals: Boolean,
    zeroTransitions: Boolean
) : Simulation() {

    private val modelAgent = ModelAgent(this)
    private val environmentAgent = EnvironmentAgent(this, modelAgent, numberOfPatients, earlyArrivals)
    val registrationAgent = RegistrationAgent(this, modelAgent, numberOfAdminWorkers)
    val examinationAgent = ExaminationAgent(this, modelAgent, numberOfDoctors)
    val vaccinationAgent = VaccinationAgent(this, modelAgent, numberOfNurses)
    val waitingAgent = WaitingAgent(this, modelAgent)
    val transferAgent = TransferAgent(this, modelAgent)
    val lunchBreakAgent = LunchBreakAgent(this, modelAgent)
    val injectionsAgent = InjectionsAgent(this, vaccinationAgent, 2)

    val registrationStats = AgentStats()
    val examinationStats = AgentStats()
    val vaccinationStats = AgentStats()
    val overallStats = OverallStats()
    val waitingStats = Stat()
    val nursesQueueLengthStats = Stat()

    init {
        VaccinationCentreProcess.useZeroDuration = zeroTransitions
    }

    override fun prepareSimulation() {
        super.prepareSimulation()

        registrationStats.clear()
        examinationStats.clear()
        vaccinationStats.clear()
        overallStats.clear()
        waitingStats.clear()
        nursesQueueLengthStats.clear()
    }

    override fun prepareReplication() {
        super.prepareReplication()
        modelAgent.runSimulation()
    }

    private fun countLastStats() {
        registrationAgent.countLastStats()
        examinationAgent.countLastStats()
        vaccinationAgent.countLastStats()
        waitingAgent.countLastStats()
        injectionsAgent.countLastStats()
    }

    override fun replicationFinished() {
        countLastStats()
        super.replicationFinished()

        registrationStats.addStatistics(registrationAgent)
        examinationStats.addStatistics(examinationAgent)
        vaccinationStats.addStatistics(vaccinationAgent)
        overallStats.addWaiting(
            registrationStats.waitingTimeStat.mean(),
            examinationStats.waitingTimeStat.mean(),
            vaccinationStats.waitingTimeStat.mean()
        )
        overallStats.addWorkload(
            registrationStats.workersWorkload.mean(), registrationAgent.workers.size,
            examinationStats.workersWorkload.mean(), examinationAgent.workers.size,
            vaccinationStats.workersWorkload.mean(), vaccinationAgent.workers.size
        )
        waitingStats.addSample(waitingAgent.averageCount)
        nursesQueueLengthStats.addSample(injectionsAgent.averageCount)

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

        if (PRINT_SIM_STATS) {
            println("-----------------")
            registrationStats.print("Registration")
            examinationStats.print("Examination")
            vaccinationStats.print("Vaccination")
            println("Waiting\nPatients count mean: " + waitingStats.mean())
            println("Injections Preparation\nNurses q length mean: " + waitingStats.mean())
        }
    }

    private fun checkFinalState() {
        if (isRunning) {
            environmentAgent.checkFinalState()
            registrationAgent.checkFinalState()
            examinationAgent.checkFinalState()
            vaccinationAgent.checkFinalState()
            waitingAgent.checkFinalState()
            injectionsAgent.checkFinalState()
            transferAgent.checkFinalState()
        }
    }

}