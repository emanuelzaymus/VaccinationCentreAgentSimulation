package sk.emanuelzaymus.agentsimulation.vaccinationcentre.workersbreak

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

class BreakManager(mySim: Simulation, myAgent: BreakAgent) :
    VaccinationCentreManager(Ids.breakManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("BreakManager", message)

        when (message.code()) {

            MessageCodes.breakStart -> startToCanteenTransfer(message as WorkersBreakMessage)

            IdList.finish -> when (message.sender().id()) {
                Ids.toCanteenTransferProcess -> startLunch(message as WorkersBreakMessage)

                Ids.lunchProcess -> startFromCanteenTransfer(message as WorkersBreakMessage)

                Ids.fromInjectionsTransferProcess -> breakDone(message as WorkersBreakMessage)
            }
        }
    }

    private fun startToCanteenTransfer(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.GOING_TO_LUNCH
        message.setAddressee(Ids.toCanteenTransferProcess)

        startContinualAssistant(message)
    }

    private fun startLunch(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.DINING
        message.setAddressee(Ids.lunchProcess)

        startContinualAssistant(message)
    }

    private fun startFromCanteenTransfer(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.GOING_FROM_LUNCH
        message.setAddressee(Ids.fromCanteenTransferProcess)

        startContinualAssistant(message)
    }

    private fun breakDone(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.FREE
        message.setCode(MessageCodes.breakEnd)

        response(message)
    }

}