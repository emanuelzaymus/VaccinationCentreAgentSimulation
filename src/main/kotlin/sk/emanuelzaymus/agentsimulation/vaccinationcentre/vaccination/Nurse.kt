package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_COUNT_TO_PREPARE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState as WS

class Nurse(id: Int, mySim: Simulation) : VaccinationCentreWorker(id, mySim) {

    override val stringName: String = "Nurse"

    var injectionsLeft: Int = INJECTIONS_COUNT_TO_PREPARE
        private set

    override var state: WS
        get() = super.state
        set(value) {
            if (value == WS.FREE && super.state == WS.GOING_FROM_INJECTIONS_PREPARATION) {
                stateField = value
                return
            }
            if (value == WS.GOING_TO_PREPARE_INJECTIONS && super.state != WS.FREE)
                throw IllegalArgumentException("Cannot assign $value when is not ${WS.FREE}.")
            if (value == WS.WAITING_TO_INJECTIONS_PREPARATION && super.state != WS.GOING_TO_PREPARE_INJECTIONS)
                throw IllegalArgumentException("Cannot assign $value when is not ${WS.GOING_TO_PREPARE_INJECTIONS}.")
            if (value == WS.PREPARING_INJECTIONS && (super.state != WS.GOING_TO_PREPARE_INJECTIONS && super.state != WS.WAITING_TO_INJECTIONS_PREPARATION))
                throw IllegalArgumentException("Cannot assign $value when is not ${WS.GOING_TO_PREPARE_INJECTIONS}/${WS.WAITING_TO_INJECTIONS_PREPARATION}.")
            if (value == WS.GOING_FROM_INJECTIONS_PREPARATION && super.state != WS.PREPARING_INJECTIONS)
                throw IllegalArgumentException("Cannot assign $value when is not ${WS.PREPARING_INJECTIONS}.")

            super.state = value
        }

    fun hasAnyInjectionLeft() = injectionsLeft > 0

    fun decrementInjectionsLeft() {
        if (injectionsLeft <= 0) {
            throw IllegalStateException("Cannot decrement injectionsLeft - is already 0")
        }
        injectionsLeft--
    }

    fun restartInjectionsLeft() {
        injectionsLeft = INJECTIONS_COUNT_TO_PREPARE
    }

}