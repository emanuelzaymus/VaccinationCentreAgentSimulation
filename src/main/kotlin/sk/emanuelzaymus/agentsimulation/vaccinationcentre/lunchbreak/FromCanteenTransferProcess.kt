package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.CANTEEN_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.CANTEEN_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class FromCanteenTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess(Ids.fromCanteenTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(CANTEEN_TRANSFER_DURATION_MIN, CANTEEN_TRANSFER_DURATION_MAX)
    }

    override val debugName = "FromCanteenTransferProcess"

    override fun getDuration(): Double = transferDuration.sample()

}