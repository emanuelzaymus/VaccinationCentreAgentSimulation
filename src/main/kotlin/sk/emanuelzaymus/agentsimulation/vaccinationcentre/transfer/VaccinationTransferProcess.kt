package sk.emanuelzaymus.agentsimulation.vaccinationcentre.transfer

import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message

class VaccinationTransferProcess(mySim: Simulation, private val myAgent: TransferAgent) :
    VaccinationCentreTransferProcess<Message>(Ids.vaccinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(C.VACCINATION_TRANSFER_DURATION_MIN, C.VACCINATION_TRANSFER_DURATION_MAX)
    }

    override val debugName = "VaccinationTransfer"

    override fun getDuration(): Double = transferDuration.sample()

    override fun startProcess(message: Message) {
        myAgent.vaccinationCount++
        super.startProcess(message)
    }

    override fun endProcess(message: Message) {
        myAgent.vaccinationCount--
        super.endProcess(message)
    }

}