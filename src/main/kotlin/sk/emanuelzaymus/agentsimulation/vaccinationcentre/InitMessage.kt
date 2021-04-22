package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation

class InitMessage(sim: Simulation) : MessageForm(sim) {

    init {
        setCode(MessageCodes.init)
    }

    override fun createCopy(): MessageForm {
        throw IllegalStateException("Cannot create a copy from InitMessage.")
    }

}