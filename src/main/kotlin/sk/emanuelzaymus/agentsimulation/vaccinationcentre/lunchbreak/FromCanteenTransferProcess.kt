package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class FromCanteenTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess<WorkersBreakMessage>(Ids.fromCanteenTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(C.CANTEEN_TRANSFER_DURATION_MIN, C.CANTEEN_TRANSFER_DURATION_MAX)
    }

    override val debugName = "FromCanteenTransferProcess"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.GOING_FROM_LUNCH
        super.startProcess(message)
    }

}