package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class VaccinationCentreTransferProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

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

    protected open fun startTransfer(message: MessageForm) {
        message.setCode(MessageCodes.transferEnd)
        hold(getDuration(), message)
    }

    protected open fun endTransfer(message: MessageForm) = assistantFinished(message)

}