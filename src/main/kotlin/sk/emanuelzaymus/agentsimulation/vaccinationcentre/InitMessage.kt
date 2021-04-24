package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation

class InitMessage(mySim: Simulation) : MessageForm(mySim) {

    init {
        setCode(MessageCodes.init)
    }

    override fun createCopy(): MessageForm {
        throw IllegalStateException("Cannot create a copy from InitMessage.")
    }

}