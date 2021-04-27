package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.InitMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreAgent

class ModelAgent(mySim: Simulation) : VaccinationCentreAgent(Ids.modelAgent, mySim, null) {

    init {
        ModelManager(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.patientArrival)
        addOwnMessage(MessageCodes.registrationDone)
        addOwnMessage(MessageCodes.examinationDone)
        addOwnMessage(MessageCodes.vaccinationDone)
        addOwnMessage(MessageCodes.waitingDone)
    }

    fun runSimulation() {
        val message = InitMessage(mySim())
        message.setAddressee(this)

        manager().notice(message)
    }

}