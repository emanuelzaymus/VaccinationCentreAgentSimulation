package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_TRANSFER_DURATION_MIN

class VaccinationTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    TransferProcess(Ids.vaccinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(VACCINATION_TRANSFER_DURATION_MIN, VACCINATION_TRANSFER_DURATION_MAX)
    }

    override val debugName = "VaccinationTransfer"

    override fun getDuration(): Double = transferDuration.sample()

}