package sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager

class WaitingManager(mySim: Simulation, private val myAgent: WaitingAgent) :
    VaccinationCentreManager(Ids.waitingManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.waiting -> startWaiting(message as Message)

            IdList.finish -> endWaiting(message as Message)
        }
    }

    private fun startWaiting(message: Message) {
        debug("WaitingManager", message)

        myAgent.incrementWaitingPatients()

        message.setAddressee(Ids.waitingProcess)
        startContinualAssistant(message)
    }

    private fun endWaiting(message: Message) {
        debug("WaitingManager", message)

        myAgent.decrementWaitingPatients()

        message.setCode(MessageCodes.waitingDone)
        response(message)
    }

}