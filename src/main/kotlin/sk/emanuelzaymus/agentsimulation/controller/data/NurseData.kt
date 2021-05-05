package sk.emanuelzaymus.agentsimulation.controller.data

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.Nurse

class NurseData(id: Int, isBusy: Boolean, workload: Double, state: String, val injectionsLeft: Int) :
    WorkerData(id, isBusy, workload, state) {

    companion object {
        fun create(w: VaccinationCentreWorker) =
            NurseData(w.id, w.isBusy, w.workloadStat.mean(), w.state.name, (w as Nurse).injectionsLeft)
    }
}