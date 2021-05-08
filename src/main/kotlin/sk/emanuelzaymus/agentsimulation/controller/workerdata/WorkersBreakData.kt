package sk.emanuelzaymus.agentsimulation.controller.workerdata

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class WorkersBreakData private constructor(w: VaccinationCentreWorker) :
    WorkerData(w.id, w.isBusy, w.hadLunchBreak, w.workloadStat.mean(), w.state.name) {

    val name: String = w.toString()

    companion object {
        fun create(w: VaccinationCentreWorker) = WorkersBreakData(w)
    }
}