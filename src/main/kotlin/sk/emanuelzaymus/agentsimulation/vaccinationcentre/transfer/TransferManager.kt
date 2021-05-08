package sk.emanuelzaymus.agentsimulation.vaccinationcentre.transfer

import OSPABA.Agent
import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

class TransferManager(mySim: Simulation, myAgent: Agent) :
    VaccinationCentreManager(Ids.transferManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("TransferManager", message)

        when (message.code()) {
            MessageCodes.examinationTransferStart -> startTransfer(Ids.examinationTransferProcess, message)

            MessageCodes.vaccinationTransferStart -> startTransfer(Ids.vaccinationTransferProcess, message)

            MessageCodes.waitingTransferStart -> startTransfer(Ids.waitingTransferProcess, message)

            IdList.finish -> when (message.sender().id()) {

                Ids.examinationTransferProcess -> transferDone(MessageCodes.examinationTransferEnd, message)

                Ids.vaccinationTransferProcess -> transferDone(MessageCodes.vaccinationTransferEnd, message)

                Ids.waitingTransferProcess -> transferDone(MessageCodes.waitingTransferEnd, message)
            }
        }
    }

    private fun startTransfer(addresseeId: Int, message: MessageForm) {
        message.setAddressee(addresseeId)

        startContinualAssistant(message)
    }

    private fun transferDone(endTransferMessageCode: Int, message: MessageForm) {
        message.setCode(endTransferMessageCode)

        response(message)
    }

}