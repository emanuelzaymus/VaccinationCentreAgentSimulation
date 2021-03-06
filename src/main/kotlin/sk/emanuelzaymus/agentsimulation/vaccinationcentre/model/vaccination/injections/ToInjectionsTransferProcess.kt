package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination.injections

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.INJECTIONS_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.INJECTIONS_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class ToInjectionsTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess<InjectionsPreparationMessage>(Ids.toInjectionsTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration
            get() = if (useZeroDuration) zeroDuration
            else UniformContinuousRNG(INJECTIONS_TRANSFER_DURATION_MIN, INJECTIONS_TRANSFER_DURATION_MAX)
    }

    override val debugName = "ToInjectionsTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.state = WorkerState.GOING_TO_PREPARE_INJECTIONS
        super.startProcess(message)
    }

}