package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.examination

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class Doctor(id: Int, mySim: Simulation) : VaccinationCentreWorker(id, mySim) {
    override val stringName: String = "Doctor"
}