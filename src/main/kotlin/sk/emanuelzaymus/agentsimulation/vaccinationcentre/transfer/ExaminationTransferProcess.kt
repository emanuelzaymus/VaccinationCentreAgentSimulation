package sk.emanuelzaymus.agentsimulation.vaccinationcentre.transfer

import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess

class ExaminationTransferProcess(mySim: Simulation, private val myAgent: TransferAgent) :
    VaccinationCentreTransferProcess<Message>(Ids.examinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(EXAMINATION_TRANSFER_DURATION_MIN, EXAMINATION_TRANSFER_DURATION_MAX)
    }

    override val debugName = "ExaminationTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: Message) {
        myAgent.examinationCount++
        super.startProcess(message)
    }

    override fun endProcess(message: Message) {
        myAgent.examinationCount--
        super.endProcess(message)
    }

}