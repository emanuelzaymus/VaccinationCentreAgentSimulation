package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState as WS

abstract class VaccinationCentreWorker(val id: Int, val workloadStat: WStat) : IBusyObject, IWStatsEntity {

    protected abstract val stringName: String

    override var isBusy: Boolean = false
        set(value) {
            if (value == field)
                throw IllegalArgumentException("Cannot reassigned property isBusy with the same value: $value.")
            field = value
            addToWorkloadStat()
        }

    protected var stateField = WS.FREE

    open var state: WS
        get() = stateField
        set(value) {
            checkWorkerState(value)
            stateField = value
        }

    private fun checkWorkerState(value: WS) {
        if (value == WS.WORKING && state != WS.FREE)
            throw IllegalArgumentException("Cannot assign $value when is not ${WS.FREE}.")
        if (value == WS.FREE && (state != WS.WORKING && state != WS.GOING_FROM_LUNCH))
            throw IllegalArgumentException("Cannot assign $value when is not ${WS.WORKING}/${WS.GOING_FROM_LUNCH}.")
        if (value == WS.GOING_TO_LUNCH && state != WS.FREE)
            throw IllegalArgumentException("Cannot assign $value when is not ${WS.FREE}.")
        if (value == WS.DINING && state != WS.GOING_TO_LUNCH)
            throw IllegalArgumentException("Cannot assign $value when is not ${WS.GOING_TO_LUNCH}.")
        if (value == WS.GOING_FROM_LUNCH && state != WS.DINING)
            throw IllegalArgumentException("Cannot assign $value when is not ${WS.DINING}.")
    }

    var hadLunchBreak = false
        private set

    var isHavingLunchBreak = false
        private set(value) {
            if (value == field)
                throw IllegalArgumentException("Cannot reassigned property isHavingLunchBreak with the same value: $value.")
            field = value
            isBusy = value
        }

    fun setLunchBreakDone() {
        if (hadLunchBreak)
            throw IllegalStateException("Cannot set hadLunchBreak true 2 times.")
        hadLunchBreak = true
        isHavingLunchBreak = false
    }

    fun setIsHavingLunchBreak() {
        if (hadLunchBreak)
            throw IllegalStateException("Worker already had lunch break.")
        if (isHavingLunchBreak)
            throw IllegalStateException("Worker is having lunch break right now.")
        isHavingLunchBreak = true
    }

    override fun restart() {
        workloadStat.clear()
        hadLunchBreak = false
    }

    override fun checkFinalState() {
        if (isHavingLunchBreak) throw IllegalStateException("Worker $this is still having lunch break.")
        if (isBusy) throw IllegalStateException("Worker $this is still busy.")
        if (!hadLunchBreak) throw IllegalStateException("Worker $this did not have lunch break.")
    }

    override fun countLastStats() = addToWorkloadStat()

    override fun toString(): String = "$stringName $id"

    private fun addToWorkloadStat() = workloadStat.addSample(if (isBusy) 1.0 else .0)

}