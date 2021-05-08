package sk.emanuelzaymus.agentsimulation.controller.workerdata

import sk.emanuelzaymus.agentsimulation.controller.roundToString
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

open class WorkerData protected constructor(
    val id: Int,
    private val isBusy: Boolean,
    private val hadLunchBreak: Boolean,
    private val workload: Double,
    val state: String
) {
    val busy: String get() = if (isBusy) "Yes" else "No"
    val lunch: String get() = if (hadLunchBreak) "Yes" else "No"
    val avgWorkload get() = workload.roundToString()

    companion object {
        fun create(w: VaccinationCentreWorker) =
            WorkerData(w.id, w.isBusy, w.hadLunchBreak, w.workloadStat.mean(), w.state.name)
    }
}