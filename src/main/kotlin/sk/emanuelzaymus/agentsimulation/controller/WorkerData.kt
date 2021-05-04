package sk.emanuelzaymus.agentsimulation.controller

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

open class WorkerData(
    val id: Int,
    private val isBusy: Boolean,
    private val workload: Double,
    val state: String
) {
    val busy: String get() = if (isBusy) "Yes" else "No"
    val avgWorkload get() = workload.roundToString()

    companion object {
        fun create(w: VaccinationCentreWorker) = WorkerData(w.id, w.isBusy, w.workloadStat.mean(), w.state.name)
    }
}