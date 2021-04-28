package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_COUNT_TO_PREPARE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker

class Nurse(id: Int, workloadStat: WStat) : VaccinationCentreWorker(id, workloadStat) {

    override val stringName: String = "Nurse"

    private var injectionsLeft: Int = INJECTIONS_COUNT_TO_PREPARE
    var isPreparing: Boolean = false

    override var isBusy: Boolean
        get() = super.isBusy || isPreparing
        set(value) {
            super.isBusy = value
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