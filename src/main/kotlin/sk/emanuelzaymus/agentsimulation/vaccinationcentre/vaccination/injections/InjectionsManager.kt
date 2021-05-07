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
            MessageCodes.injectionsPreparationStart -> startToInjectionsTransfer(message)

            IdList.finish -> when (message.sender().id()) {
                Ids.toInjectionsTransferProcess -> tryStartPreparationProcess(message as InjectionsPreparationMessage)

                Ids.injectionsPreparationProcess -> preparationProcessDone(message)

                Ids.fromInjectionsTransferProcess -> injectionsPreparationDone(message as InjectionsPreparationMessage)
            }
        }
    }

    private fun startToInjectionsTransfer(message: MessageForm) {
        message.setAddressee(Ids.toInjectionsTransferProcess)

        startContinualAssistant(message)
    }

    private fun tryStartPreparationProcess(message: InjectionsPreparationMessage) {
        if (myAgent.canStartAnotherPreparation())
            startPreparationProcess(message)
        else {
            message.nurse!!.state = WorkerState.WAITING_TO_INJECTIONS_PREPARATION
            myAgent.queue.enqueue(message)
        }
    }

    private fun preparationProcessDone(message: MessageForm) {
        myAgent.preparationDone()

        if (myAgent.queue.size > 0)
            startPreparationProcess(myAgent.queue.dequeue())

        startFromInjectionsTransfer(message)
    }

    private fun startFromInjectionsTransfer(message: MessageForm) {
        message.setAddressee(Ids.fromInjectionsTransferProcess)

        startContinualAssistant(message)
    }

    private fun injectionsPreparationDone(message: InjectionsPreparationMessage) {
        message.nurse!!.state = WorkerState.FREE
        message.setCode(MessageCodes.injectionsPreparationEnd)

        response(message)
    }

    private fun startPreparationProcess(message: MessageForm) {
        myAgent.startPreparation()

        message.setAddressee(Ids.injectionsPreparationProcess)
        startContinualAssistant(message)
    }

}