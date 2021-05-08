package sk.emanuelzaymus.agentsimulation.controller.workerdata

import sk.emanuelzaymus.agentsimulation.controller.roundToString
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

open class WorkerData protected constructor(worker: VaccinationCentreWorker) {

    val id: Int = worker.id
    private val isBusy: Boolean = worker.isBusy
    private val hadLunchBreak: Boolean = worker.hadLunchBreak
    private val workload: Double = worker.workloadStat.mean()
    val state: String = worker.state.name

    val busy: String get() = if (isBusy) "Yes" else "No"
    val lunch: String get() = if (hadLunchBreak) "Yes" else "No"
    val avgWorkload get() = workload.roundToString()

    companion object {
        fun create(worker: VaccinationCentreWorker) = WorkerData(worker)
    }

}