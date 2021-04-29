package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.CommonAgent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class TransferProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) : Process(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val transferStartMsgCode: Int
    protected abstract val transferEndMsgCode: Int

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            // ModelManager - start transfer
            transferStartMsgCode -> startTransfer(message)

            transferEndMsgCode -> endTransfer(message)
        }
    }

    private fun startTransfer(message: MessageForm) {
        message.setCode(transferEndMsgCode)

        hold(getDuration(), message)
    }

    private fun endTransfer(message: MessageForm) = assistantFinished(message)

}