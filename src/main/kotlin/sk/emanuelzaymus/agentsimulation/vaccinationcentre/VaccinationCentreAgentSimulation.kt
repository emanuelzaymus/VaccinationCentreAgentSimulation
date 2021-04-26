package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.ExaminationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.RegistrationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent

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

    private val waitingTimeStat = Stat()
    private val queueLengthStat = Stat()
    private val adminWorkersWorkload = Stat()

    override fun prepareSimulation() {
        super.prepareSimulation()
        waitingTimeStat.clear()
        queueLengthStat.clear()
        adminWorkersWorkload.clear()
    }

    override fun prepareReplication() {
        super.prepareReplication()
        modelAgent.runSimulation()
    }

    override fun replicationFinished() {
        super.replicationFinished()

        waitingTimeStat.addSample(registrationAgent.waitingTimeStat.mean())
        println(
            "R${currentReplication()} - Avg waiting time: ${waitingTimeStat.mean()} " +
                    "(${registrationAgent.waitingTimeStat.mean()})"
        )

        queueLengthStat.addSample(registrationAgent.queueLengthStat().mean())
        println(
            "R${currentReplication()} - Avg queue length: ${queueLengthStat.mean()} " +
                    "(${registrationAgent.queueLengthStat().mean()})"
        )

        adminWorkersWorkload.addSample(registrationAgent.adminsWorkloadMean())
        println(
            "R${currentReplication()} - Avg queue length: ${adminWorkersWorkload.mean()} " +
                    "(${registrationAgent.adminsWorkloadMean()})"
        )

        registrationAgent.checkFinalState()
        environmentAgent.checkFinalState()
    }

    override fun simulationFinished() {
        super.simulationFinished()
        println("Waiting time mean: ${waitingTimeStat.mean()}") // ~ 16
        println("Queue length mean: ${queueLengthStat.mean()}") // ~ 3.2
        println("Admins workload mean: ${adminWorkersWorkload.mean()}")
    }

}