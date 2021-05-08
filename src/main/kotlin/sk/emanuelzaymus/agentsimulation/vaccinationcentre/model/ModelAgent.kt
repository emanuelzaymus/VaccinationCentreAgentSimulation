package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.InitMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class ModelAgent(private val mySim: Simulation) : VaccinationCentreAgent(Ids.modelAgent, mySim, null) {

    init {
        ModelManager(mySim, this)

        addOwnMessage(MessageCodes.init)

        addOwnMessage(MessageCodes.patientArrival)
        addOwnMessage(MessageCodes.registrationEnd)
        addOwnMessage(MessageCodes.examinationEnd)
        addOwnMessage(MessageCodes.vaccinationEnd)
        addOwnMessage(MessageCodes.waitingEnd)

        addOwnMessage(MessageCodes.examinationTransferEnd)
        addOwnMessage(MessageCodes.vaccinationTransferEnd)
        addOwnMessage(MessageCodes.waitingTransferEnd)

        addOwnMessage(MessageCodes.lunchBreakStart)
        addOwnMessage(MessageCodes.lunchBreakEnd)
    }

    fun runSimulation() {
        val message = InitMessage(mySim)
        message.setAddressee(this)

        manager().notice(message)
    }

}