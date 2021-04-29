package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class TransferProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) : Process(id, mySim, myAgent) {

    protected abstract val debugName: String

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            // ModelManager - start transfer
            IdList.start -> startTransfer(message)

            MessageCodes.transferEnd -> endTransfer(message)
        }
    }

    private fun startTransfer(message: MessageForm) {
        message.setCode(MessageCodes.transferEnd)
        hold(getDuration(), message)
    }

    private fun endTransfer(message: MessageForm) = assistantFinished(message)

}