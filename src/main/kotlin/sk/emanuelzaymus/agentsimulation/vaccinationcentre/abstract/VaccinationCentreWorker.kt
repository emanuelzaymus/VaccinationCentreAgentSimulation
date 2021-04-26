package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject

abstract class VaccinationCentreWorker(val workloadStat: WStat) : IBusyObject {

    protected abstract val stringName: String

    override var isBusy: Boolean = false
        set(value) {
            if (value == field)
                throw IllegalArgumentException("Cannot reassigned isBusy with the same value.")
            field = value
            workloadStat.addSample(if (value) 1.0 else .0)
        }

    override fun restart() = workloadStat.clear()

    override fun checkFinalState() {
        if (isBusy) throw IllegalStateException("This worker is still working.")
    }

    override fun toString(): String = "Worker: $stringName"

}