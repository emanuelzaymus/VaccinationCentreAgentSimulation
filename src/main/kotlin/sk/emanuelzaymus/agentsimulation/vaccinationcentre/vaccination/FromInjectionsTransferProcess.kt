package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.TransferProcess

class FromInjectionsTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    TransferProcess(Ids.fromInjectionsTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(INJECTIONS_TRANSFER_DURATION_MIN, INJECTIONS_TRANSFER_DURATION_MAX)
    }

    override val debugName = "FromInjectionsTransfer"

    override fun getDuration(): Double = transferDuration.sample()

}