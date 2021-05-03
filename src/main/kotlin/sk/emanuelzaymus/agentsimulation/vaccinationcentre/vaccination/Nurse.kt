package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_COUNT_TO_PREPARE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState

class Nurse(id: Int, workloadStat: WStat) : VaccinationCentreWorker(id, workloadStat) {

    override val stringName: String = "Nurse"

    private var injectionsLeft: Int = INJECTIONS_COUNT_TO_PREPARE

    override var state: WorkerState
        get() = super.state
        set(value) {
            if (value == WorkerState.GOING_TO_PREPARE_INJECTIONS && super.state != WorkerState.FREE)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.FREE}.")
            if (value == WorkerState.WAITING_TO_INJECTIONS_PREPARATION && super.state != WorkerState.GOING_TO_PREPARE_INJECTIONS)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.GOING_TO_PREPARE_INJECTIONS}.")
            if (value == WorkerState.PREPARING_INJECTIONS && (super.state != WorkerState.GOING_TO_PREPARE_INJECTIONS && super.state != WorkerState.WAITING_TO_INJECTIONS_PREPARATION))
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.GOING_TO_PREPARE_INJECTIONS}/${WorkerState.WAITING_TO_INJECTIONS_PREPARATION}.")
            if (value == WorkerState.GOING_FROM_INJECTIONS_PREPARATION && super.state != WorkerState.PREPARING_INJECTIONS)
                throw IllegalArgumentException("Cannot assign $value when is not ${WorkerState.PREPARING_INJECTIONS}.")

            super.state = value
        }

    fun hasAnyInjectionLeft() = injectionsLeft > 0

    fun decrementInjectionsLeft() {
        if (injectionsLeft <= 0) {
            throw IllegalStateException("Cannot decrement injectionsLeft - is already 0")
        }
        injectionsLeft--
    }

    fun restartInjectionLeft() {
        injectionsLeft = INJECTIONS_COUNT_TO_PREPARE
    }

}