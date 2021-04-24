package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class EnvironmentAgent(mySim: Simulation, parent: Agent) : Agent(Ids.environmentAgent, mySim, parent) {

    init {
        EnvironmentManager(mySim, this)
        PatientArrivalsScheduler(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.getNewPatient)
        addOwnMessage(MessageCodes.patientLeaving)
    }

}