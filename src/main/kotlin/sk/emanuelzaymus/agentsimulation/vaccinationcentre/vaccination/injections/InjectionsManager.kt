package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

class InjectionsManager(mySim: Simulation, private val myAgent: InjectionsAgent) :
    VaccinationCentreManager(Ids.injectionsManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("InjectionsManager", message)

        when (message.code()) {

            MessageCodes.injectionsPreparationStart -> tryStartPreparation(message as InjectionsPreparationMessage)
            // InjectionsPreparationProcess - preparation done
            IdList.finish -> preparationDone(message as InjectionsPreparationMessage)
        }
    }

    private fun tryStartPreparation(message: InjectionsPreparationMessage) {
        if (myAgent.canStartAnotherPreparation())
            startPreparation(message)
        else {
            message.nurse!!.state = WorkerState.WAITING_TO_INJECTIONS_PREPARATION
            myAgent.queue.enqueue(message)
        }
    }

    private fun preparationDone(message: InjectionsPreparationMessage) {
        myAgent.preparationDone()

        if (myAgent.queue.size > 0)
            startPreparation(myAgent.queue.dequeue())

        message.setCode(MessageCodes.injectionsPreparationEnd)
        response(message)
    }

    private fun startPreparation(message: InjectionsPreparationMessage) {
        myAgent.startPreparation()

        message.setAddressee(Ids.injectionsPreparationProcess)
        startContinualAssistant(message)
    }

}