package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class AdministrativeWorker(id: Int, workloadStat: WStat) : VaccinationCentreWorker(id, workloadStat) {
    override val stringName: String = "Admin"
}