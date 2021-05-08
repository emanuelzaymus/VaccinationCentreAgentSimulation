package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.INJECTIONS_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.INJECTIONS_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class FromInjectionsTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess<InjectionsPreparationMessage>(Ids.fromInjectionsTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(INJECTIONS_TRANSFER_DURATION_MIN, INJECTIONS_TRANSFER_DURATION_MAX)
    }

    override val debugName = "FromInjectionsTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.state = WorkerState.GOING_FROM_INJECTIONS_PREPARATION
        super.startProcess(message)
    }

}