package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.RegistrationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent

class VaccinationCentreAgentSimulation : Simulation() {

    private val modelAgent = ModelAgent(this)
    private val environmentAgent = EnvironmentAgent(this, modelAgent)
    private val registrationAgent = RegistrationAgent(this, modelAgent)

    private lateinit var waitingTimeStat: Stat
    private lateinit var queueLengthStat: Stat

    override fun prepareSimulation() {
        super.prepareSimulation()
        waitingTimeStat = Stat()
        queueLengthStat = Stat()
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

        registrationAgent.checkFinalState()
        environmentAgent.checkFinalState()
    }

    override fun simulationFinished() {
        super.simulationFinished()
        println("Waiting time mean: ${waitingTimeStat.mean()}") // ~ 16
        println("Queue length mean: ${queueLengthStat.mean()}") // ~ 3.2
    }

}