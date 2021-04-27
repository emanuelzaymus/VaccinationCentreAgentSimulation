package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker

class Doctor(id: Int, workloadStat: WStat) : VaccinationCentreWorker(id, workloadStat) {
    override val stringName: String = "Doctor"
}