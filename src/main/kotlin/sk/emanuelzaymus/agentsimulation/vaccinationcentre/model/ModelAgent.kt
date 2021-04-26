package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.InitMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ModelAgent(mySim: Simulation) : Agent(Ids.modelAgent, mySim, null) {

    init {
        ModelManager(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.patientArrival)
        addOwnMessage(MessageCodes.patientRegistrationDone)
        addOwnMessage(MessageCodes.examinationDone)
    }

    fun runSimulation() {
        val message = InitMessage(mySim())
        message.setAddressee(this)

        manager().notice(message)
    }

}