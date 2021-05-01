package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject

abstract class VaccinationCentreWorker(private val id: Int, val workloadStat: WStat) : IBusyObject {

    protected abstract val stringName: String

    var isDining: Boolean = false
    var inTransfer: Boolean = false // TODO: prerob !!! na state
//    var state: WorkerState = WorkerState.FREE

    override var isBusy: Boolean = false
        get() = field || isDining || inTransfer
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

    override fun toString(): String = "$stringName $id"

}