package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.Nurse

abstract class VaccinationCentreWorker(val id: Int, val workloadStat: WStat) : IBusyObject, IWStatsEntity {

    protected abstract val stringName: String

    override var isBusy: Boolean = false
        protected set(value) {
            field = value
            workloadStat.addSample(if (value) 1.0 else .0)
        }

    open var state = WorkerState.FREE
        set(value) {
            if (this is Nurse && value == WorkerState.FREE && field == WorkerState.GOING_FROM_INJECTIONS_PREPARATION) {
                field = value
                isBusy = field.isBusy()
                return
            }
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
            isBusy = field.isBusy()
        }

    override fun restart() = workloadStat.clear()

    override fun checkFinalState() {
        if (isBusy) throw IllegalStateException("This worker is still working.")
    }

    override fun countLastStats() {
        isBusy = isBusy
    }

    override fun toString(): String = "$stringName $id"

}