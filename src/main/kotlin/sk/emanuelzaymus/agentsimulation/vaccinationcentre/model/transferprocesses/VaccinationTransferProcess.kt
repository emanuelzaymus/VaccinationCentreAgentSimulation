package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transferprocesses

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class VaccinationTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess<Message>(Ids.vaccinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(VACCINATION_TRANSFER_DURATION_MIN, VACCINATION_TRANSFER_DURATION_MAX)
    }

    override val debugName = "VaccinationTransfer"

    override fun getDuration(): Double = transferDuration.sample()

}