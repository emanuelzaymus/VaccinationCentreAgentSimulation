package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ModelManager(id: Int = Ids.modelManager, mySim: Simulation, myAgent: Agent) : Manager(id, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.init -> {
                message.setAddressee(mySim().findAgent(Ids.environmentAgent))

                notice(message)
            }

            MessageCodes.patientArrival -> {
                message.setCode(MessageCodes.patientRegistration)
                message.setAddressee(mySim().findAgent(Ids.registrationAgent))

                request(message)
            }

            MessageCodes.patientRegistrationDone -> {
                message.setCode(MessageCodes.patientLeaving)
                message.setAddressee(mySim().findAgent(Ids.environmentAgent))

                notice(message)
            }
        }
    }

}