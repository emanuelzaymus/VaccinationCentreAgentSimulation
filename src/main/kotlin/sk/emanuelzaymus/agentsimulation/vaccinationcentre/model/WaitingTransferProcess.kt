package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.WAITING_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.WAITING_TRANSFER_DURATION_MIN

class WaitingTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    TransferProcess(Ids.waitingTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(WAITING_TRANSFER_DURATION_MIN, WAITING_TRANSFER_DURATION_MAX)
    }

    override val debugName = "WaitingTransfer"

    override fun getDuration(): Double = transferDuration.sample()

}