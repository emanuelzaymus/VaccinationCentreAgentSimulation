package sk.emanuelzaymus.agentsimulation.controller.workerdata

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination.Nurse

class NurseData private constructor(nurse: Nurse) : WorkerData(nurse) {

    val injectionsLeft: Int = nurse.injectionsLeft

    companion object {
        fun create(worker: VaccinationCentreWorker) = NurseData(worker as Nurse)
    }

}