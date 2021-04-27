package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker

class Nurse(id: Int, workloadStat: WStat) : VaccinationCentreWorker(id, workloadStat) {
    override val stringName: String = "Nurse"
}