package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.Nurse

class InjectionsPreparationMessage(mySim: Simulation) : VaccinationCentreMessage(mySim) {

    var nurse: Nurse? = null

    override fun restart() {
        nurse = null
    }

    override fun toString() = "${super.toString()} $nurse (${deliveryTime()})"

}