package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.lunchbreak

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.CANTEEN_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.CANTEEN_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class FromCanteenTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess<WorkersBreakMessage>(Ids.fromCanteenTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration
            get() = if (useZeroDuration) zeroDuration
            else UniformContinuousRNG(CANTEEN_TRANSFER_DURATION_MIN, CANTEEN_TRANSFER_DURATION_MAX)
    }

    override val debugName = "FromCanteenTransferProcess"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.GOING_FROM_LUNCH
        super.startProcess(message)
    }

}