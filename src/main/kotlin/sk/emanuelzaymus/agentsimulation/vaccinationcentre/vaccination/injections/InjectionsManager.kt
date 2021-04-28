package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

class InjectionsManager(mySim: Simulation, private val myAgent: InjectionsAgent) :
    VaccinationCentreManager(Ids.injectionsManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("InjectionsManager", message)

        when (message.code()) {

            MessageCodes.injectionsPreparationStart -> tryStartPreparation(message)
            // InjectionsPreparationProcess - preparation done
            IdList.finish -> preparationDone(message)
        }
    }

    private fun tryStartPreparation(message: MessageForm) {
        if (myAgent.canStartAnotherPreparation())
            startPreparation(message)
        else
            myAgent.queue.enqueue(message as InjectionsPreparationMessage)
    }

    private fun preparationDone(message: MessageForm) {
        myAgent.preparationDone()

        if (myAgent.queue.size > 0)
            startPreparation(myAgent.queue.dequeue())

        message.setCode(MessageCodes.injectionsPreparationEnd)
        response(message)
    }

    private fun startPreparation(message: MessageForm) {
        myAgent.startPreparation()

        message.setAddressee(Ids.injectionsPreparationProcess)
        startContinualAssistant(message)
    }

}