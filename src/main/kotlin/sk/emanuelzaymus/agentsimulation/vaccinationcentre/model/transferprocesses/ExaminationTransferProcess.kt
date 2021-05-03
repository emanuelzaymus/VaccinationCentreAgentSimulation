package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transferprocesses

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.EXAMINATION_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.EXAMINATION_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class ExaminationTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess<Message>(Ids.examinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(EXAMINATION_TRANSFER_DURATION_MIN, EXAMINATION_TRANSFER_DURATION_MAX)
    }

    override val debugName = "ExaminationTransfer"

    override fun getDuration(): Double = transferDuration.sample()

}