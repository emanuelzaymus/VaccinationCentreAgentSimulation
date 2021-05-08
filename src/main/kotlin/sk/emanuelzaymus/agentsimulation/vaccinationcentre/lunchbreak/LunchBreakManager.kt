package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

class LunchBreakManager(mySim: Simulation, private val myAgent: LunchBreakAgent) :
    VaccinationCentreManager(Ids.lunchBreakManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("LunchBreakManager", message)

        when (message.code()) {
            MessageCodes.lunchBreakStart -> startLunchBreak(message as WorkersBreakMessage)

            IdList.finish -> when (message.sender().id()) {
                Ids.toCanteenTransferProcess -> startProcess(Ids.lunchProcess, message)

                Ids.lunchProcess -> startProcess(Ids.fromCanteenTransferProcess, message)

                Ids.fromCanteenTransferProcess -> breakDone(message as WorkersBreakMessage)
            }
        }
    }

    private fun startLunchBreak(message: WorkersBreakMessage) {
        myAgent.workers.add(message.worker!!)
        startProcess(Ids.toCanteenTransferProcess, message)
    }

    private fun startProcess(processId: Int, message: MessageForm) {
        message.setAddressee(processId)
        startContinualAssistant(message)
    }

    private fun breakDone(message: WorkersBreakMessage) {
        val worker = message.worker!!
        myAgent.workers.remove(worker)

        worker.state = WorkerState.FREE
        message.setCode(MessageCodes.lunchBreakEnd)

        response(message)
    }

}