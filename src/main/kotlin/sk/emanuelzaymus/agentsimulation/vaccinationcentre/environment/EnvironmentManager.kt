package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class EnvironmentManager(id: Int = Ids.environmentManager, mySim: Simulation, myAgent: Agent) :
    Manager(id, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.init -> {
                debug("EnvironmentManager - init")
                startPatientScheduling(message)
            }

            IdList.finish -> {
                debug("EnvironmentManager - finish")
                message.setCode(MessageCodes.patientArrival)
                message.setAddressee(mySim().findAgent(Ids.modelAgent))

                notice(message)
            }

            MessageCodes.patientLeaving -> {
                debug("EnvironmentManager - patientLeaving")
                message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))

                notice(message)
            }
        }
    }

    private fun startPatientScheduling(message: MessageForm) {
        message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))
        startContinualAssistant(message)
    }

}