package sk.emanuelzaymus.agentsimulation.controller.workerdata

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class WorkersBreakData private constructor(worker: VaccinationCentreWorker) : WorkerData(worker) {

    val name: String = worker.toString()

    companion object {
        fun create(w: VaccinationCentreWorker) = WorkersBreakData(w)
    }

}