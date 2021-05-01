package sk.emanuelzaymus.agentsimulation.vaccinationcentre.workersbreak

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.CANTEEN_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.CANTEEN_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class ToCanteenTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess(Ids.toCanteenTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(CANTEEN_TRANSFER_DURATION_MIN, CANTEEN_TRANSFER_DURATION_MAX)
    }

    override val debugName = "ToCanteenTransferProcess"

    override fun getDuration(): Double = transferDuration.sample()

}