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

class ToInjectionsTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess(Ids.toInjectionsTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration = UniformContinuousRNG(INJECTIONS_TRANSFER_DURATION_MIN, INJECTIONS_TRANSFER_DURATION_MAX)
    }

    override val debugName = "ToInjectionsTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: MessageForm) {
        (message as InjectionsPreparationMessage).nurse!!.state = WorkerState.GOING_TO_PREPARE_INJECTIONS
        super.startProcess(message)
    }

}