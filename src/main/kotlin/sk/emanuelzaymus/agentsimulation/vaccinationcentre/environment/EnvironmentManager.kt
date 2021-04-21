package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class EnvironmentManager(id: Int = Ids.environmentManager, mySim: Simulation, myAgent: Agent) :
    Manager(id, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.init -> startVehicleScheduling()

            IdList.finish -> {
                message.setCode(MessageCodes.patientArrival)
                message.setAddressee(mySim().findAgent(Ids.modelAgent))

                notice(message)
            }
        }
    }

    private fun startVehicleScheduling() {
        val message = Message(mySim())
        message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))

        startContinualAssistant(message)
    }

}