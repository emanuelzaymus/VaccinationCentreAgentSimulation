package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreMessage

class InitMessage(mySim: Simulation) : VaccinationCentreMessage(mySim) {

    init {
        setCode(MessageCodes.init)
    }

    override fun restart() {}

    override fun toString() = "${super.toString()} (${deliveryTime()})"

}
