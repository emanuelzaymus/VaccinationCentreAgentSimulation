package sk.emanuelzaymus.agentsimulation.vaccinationcentre.transfer

import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class WaitingTransferProcess(mySim: Simulation, private val myAgent: TransferAgent) :
    VaccinationCentreTransferProcess<Message>(Ids.waitingTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(WAITING_TRANSFER_DURATION_MIN, WAITING_TRANSFER_DURATION_MAX)
    }

    override val debugName = "WaitingTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: Message) {
        myAgent.waitingCount++
        super.startProcess(message)
    }

    override fun endProcess(message: Message) {
        myAgent.waitingCount--
        super.endProcess(message)
    }

}