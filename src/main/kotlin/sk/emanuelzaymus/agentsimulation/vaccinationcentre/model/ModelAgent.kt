package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.InitMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.PatientMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ModelAgent(mySim: Simulation, parent: Agent? = null) : Agent(Ids.modelAgent, mySim, parent) {

    init {
        ModelManager(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.patientArrival)
        addOwnMessage(MessageCodes.patientRegistrationDone)
    }

    fun runSimulation() {
        val message = InitMessage(mySim())
        message.setAddressee(this)

        manager().notice(message)
    }

}