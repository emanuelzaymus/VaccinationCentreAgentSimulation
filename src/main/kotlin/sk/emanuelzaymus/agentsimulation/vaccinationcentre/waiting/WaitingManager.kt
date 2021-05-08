package sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager

class WaitingManager(mySim: Simulation, private val myAgent: WaitingAgent) :
    VaccinationCentreManager(Ids.waitingManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("WaitingManager", message)

        when (message.code()) {
            MessageCodes.waitingStart -> startWaiting(message as Message)
            // WaitingProcess - waiting done
            IdList.finish -> waitingDone(message as Message)
        }
    }

    private fun startWaiting(message: Message) {
        myAgent.incrementWaitingPatients()

        message.setAddressee(Ids.waitingProcess)
        startContinualAssistant(message)
    }

    private fun waitingDone(message: Message) {
        myAgent.decrementWaitingPatients()

        message.setCode(MessageCodes.waitingEnd)
        response(message)
    }

}