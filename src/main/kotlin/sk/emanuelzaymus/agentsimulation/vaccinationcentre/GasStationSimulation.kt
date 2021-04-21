package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.EnvironmentAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.gasstation.GasStationAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.ModelAgent

class GasStationSimulation : Simulation() {

    private val modelAgent = ModelAgent(mySim = this)
    private val environmentAgent = EnvironmentAgent(mySim = this, parent = modelAgent)
    private val gasStationAgent = GasStationAgent(mySim = this, parent = modelAgent)

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

        waitingTimeStat.addSample(gasStationAgent.waitingTimeStat.mean())
        println(
            "R${currentReplication()} - Avg waiting time: ${waitingTimeStat.mean()} " +
                    "(${gasStationAgent.waitingTimeStat.mean()})"
        )

        queueLengthStat.addSample(gasStationAgent.queueLengthStat().mean())
        println(
            "R${currentReplication()} - Avg queue length: ${queueLengthStat.mean()} " +
                    "(${gasStationAgent.queueLengthStat().mean()})"
        )
    }

    override fun simulationFinished() {
        super.simulationFinished()
        println("Waiting time mean: ${waitingTimeStat.mean()}") // ~ 16
        println("Queue length mean: ${queueLengthStat.mean()}") // ~ 3.2
    }

}