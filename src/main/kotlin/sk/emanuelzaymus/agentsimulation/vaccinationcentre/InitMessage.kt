package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreMessage

class InitMessage(mySim: Simulation) : VaccinationCentreMessage(mySim) {

    init {
        setCode(MessageCodes.init)
    }

    override fun createCopy(): MessageForm = InitMessage(mySim())

    override fun restart() {}

    override fun toString() = "${super.toString()} (${deliveryTime()})"

}
