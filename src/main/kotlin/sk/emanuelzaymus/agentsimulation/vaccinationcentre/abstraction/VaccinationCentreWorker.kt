package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject

abstract class VaccinationCentreWorker(private val id: Int, val workloadStat: WStat) : IBusyObject {

    protected abstract val stringName: String

    var isDining: Boolean = false
    var inTransfer: Boolean = false // TODO: prerob !!! na state

    override var isBusy: Boolean = false
        get() =  field || isDining || inTransfer && state.isBusy()
        set(value) {
            if (value == field)
                throw IllegalArgumentException("Cannot reassigned isBusy with the same value.")
            field = value
            workloadStat.addSample(if (value) 1.0 else .0)
        }

    var state = WorkerState.FREE
        set(value) {
            if (value == WorkerState.WORKING && field != WorkerState.FREE)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.FREE}.")
            if (value == WorkerState.FREE && (field != WorkerState.WORKING && field != WorkerState.GOING_FROM_LUNCH))
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.WORKING}/${WorkerState.GOING_FROM_LUNCH}.")
            if (value == WorkerState.GOING_TO_LUNCH && field != WorkerState.FREE)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.FREE}.")
            if (value == WorkerState.DINING && field != WorkerState.GOING_TO_LUNCH)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.GOING_TO_LUNCH}.")
            if (value == WorkerState.GOING_FROM_LUNCH && field != WorkerState.DINING)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.DINING}.")

            field = value
        }

    override fun restart() = workloadStat.clear()

    override fun checkFinalState() {
        if (isBusy) throw IllegalStateException("This worker is still working.")
    }

    override fun toString(): String = "$stringName $id"

}