package sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreMessage

/**
 * Initial message.
 */
class InitMessage(private val mySim: Simulation) : VaccinationCentreMessage(mySim) {

    init {
        setCode(MessageCodes.init)
    }

    override fun createCopy(): MessageForm = InitMessage(mySim)

    override fun restart() {}

    override fun toString() = "${super.toString()} (${deliveryTime()})"

}
