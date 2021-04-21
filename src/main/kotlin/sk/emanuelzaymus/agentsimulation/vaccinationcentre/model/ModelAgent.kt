package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ModelAgent(id: Int = Ids.modelAgent, mySim: Simulation, parent: Agent? = null) : Agent(id, mySim, parent) {

    init {
        ModelManager(mySim = mySim, myAgent = this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.vehicleArrival)
        addOwnMessage(MessageCodes.vehicleServiceDone)
    }

    fun runSimulation() {
        val message = Message(mySim())
        message.setCode(MessageCodes.init)
        message.setAddressee(this)

        manager().notice(message)
    }

}