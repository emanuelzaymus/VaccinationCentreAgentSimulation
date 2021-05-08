package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.registration

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class AdministrativeWorker(id: Int, mySim: Simulation) : VaccinationCentreWorker(id, mySim) {
    override val stringName: String = "Admin"
}