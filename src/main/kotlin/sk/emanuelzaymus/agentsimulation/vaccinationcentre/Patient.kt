package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject
import sk.emanuelzaymus.agentsimulation.utils.stopwatch.Stopwatch

data class Patient(val mySim: Simulation) : IPooledObject {

    private val stopwatch = Stopwatch()
    private var id: Int = nextId

    companion object {
        private var nextId = 1
            get() = field++
    }

    fun startWaiting() = stopwatch.start(mySim.currentTime())

    fun stopWaiting() = stopwatch.stop(mySim.currentTime())

    fun getWaitingTotal(): Double = stopwatch.getElapsedTime()

    fun restartWaiting() = stopwatch.restart()

    override fun restart() {
        id = nextId
        stopwatch.restart()
    }

}