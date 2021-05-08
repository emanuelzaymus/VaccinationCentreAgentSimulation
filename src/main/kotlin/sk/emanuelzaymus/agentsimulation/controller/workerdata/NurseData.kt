package sk.emanuelzaymus.agentsimulation.controller.workerdata

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.Nurse

class NurseData private constructor(
    id: Int, isBusy: Boolean, hadLunchBreak: Boolean, workload: Double, state: String, val injectionsLeft: Int
) : WorkerData(id, isBusy, hadLunchBreak, workload, state) {

    companion object {
        fun create(w: VaccinationCentreWorker) =
            NurseData(w.id, w.isBusy, w.hadLunchBreak, w.workloadStat.mean(), w.state.name, (w as Nurse).injectionsLeft)
    }
}