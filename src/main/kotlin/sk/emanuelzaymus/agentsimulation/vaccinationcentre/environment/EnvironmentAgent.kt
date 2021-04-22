package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class EnvironmentAgent(id: Int = Ids.environmentAgent, mySim: Simulation, parent: Agent) : Agent(id, mySim, parent) {

    init {
        EnvironmentManager(mySim = mySim, myAgent = this)
        PatientArrivalsScheduler(mySim = mySim, myAgent = this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.getNewPatient)
        addOwnMessage(MessageCodes.patientLeaving)
    }

}