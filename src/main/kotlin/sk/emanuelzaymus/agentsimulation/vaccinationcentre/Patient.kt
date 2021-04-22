package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject

data class Patient(var waitingStart: Double = .0, var waitingTotal: Double = .0) : IPooledObject {

    var id: Int = nextId

    companion object {
        private var nextId = 1
            get() = field++
    }

    override fun restart() {
        id = nextId
        waitingStart = .0
        waitingTotal = .0
    }

}