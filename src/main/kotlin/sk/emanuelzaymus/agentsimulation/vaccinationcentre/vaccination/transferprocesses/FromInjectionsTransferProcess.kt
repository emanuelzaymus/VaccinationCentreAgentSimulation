package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.transferprocesses

import OSPABA.CommonAgent
import OSPABA.MessageForm
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.INJECTIONS_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsPreparationMessage

class FromInjectionsTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess(Ids.fromInjectionsTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(INJECTIONS_TRANSFER_DURATION_MIN, INJECTIONS_TRANSFER_DURATION_MAX)
    }

    override val debugName = "FromInjectionsTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startTransfer(message: MessageForm) {
        (message as InjectionsPreparationMessage).nurse!!.state = WorkerState.GOING_FROM_INJECTIONS_PREPARATION
        super.startTransfer(message)
    }

    override fun endTransfer(message: MessageForm) {
        (message as InjectionsPreparationMessage).nurse!!.state = WorkerState.FREE
        super.endTransfer(message)
    }

}