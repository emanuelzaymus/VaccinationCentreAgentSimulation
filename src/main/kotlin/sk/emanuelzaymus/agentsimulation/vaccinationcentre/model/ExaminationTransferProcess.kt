package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.EXAMINATION_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.EXAMINATION_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ExaminationTransferProcess(mySim: Simulation, myAgent: CommonAgent) :
    TransferProcess(Ids.examinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(EXAMINATION_TRANSFER_DURATION_MIN, EXAMINATION_TRANSFER_DURATION_MAX)
    }

    override val debugName = "ExaminationTransfer"
    override val transferStartMsgCode = MessageCodes.examinationTransferStart
    override val transferEndMsgCode = MessageCodes.examinationTransferEnd

    override fun getDuration(): Double = transferDuration.sample()

}