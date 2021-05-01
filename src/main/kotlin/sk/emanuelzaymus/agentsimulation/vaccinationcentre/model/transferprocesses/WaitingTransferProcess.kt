package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transferprocesses

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.WAITING_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.WAITING_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class WaitingTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess(Ids.waitingTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(WAITING_TRANSFER_DURATION_MIN, WAITING_TRANSFER_DURATION_MAX)
    }

    override val debugName = "WaitingTransfer"

    override fun getDuration(): Double = transferDuration.sample()

}