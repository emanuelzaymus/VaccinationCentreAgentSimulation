package sk.emanuelzaymus.agentsimulation.vaccinationcentre.transfer

import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer.VaccinationCentreTransferProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.VACCINATION_TRANSFER_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.VACCINATION_TRANSFER_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message

class VaccinationTransferProcess(mySim: Simulation, private val myAgent: TransferAgent) :
    VaccinationCentreTransferProcess<Message>(Ids.vaccinationTransferProcess, mySim, myAgent) {

    companion object {
        val transferDuration =
            UniformContinuousRNG(VACCINATION_TRANSFER_DURATION_MIN, VACCINATION_TRANSFER_DURATION_MAX)
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