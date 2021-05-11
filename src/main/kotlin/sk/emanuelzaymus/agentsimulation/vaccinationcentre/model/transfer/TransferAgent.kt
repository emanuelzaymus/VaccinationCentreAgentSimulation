package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transfer

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent
import kotlin.math.abs

/**
 * Does transfer of patient to examination, vaccination and waiting room.
 */
class TransferAgent(mySim: Simulation, parent: Agent) : VaccinationCentreAgent(Ids.transferAgent, mySim, parent) {

    var examinationCount = 0
        set(value) {
            checkCount(value, field)
            field = value
        }

    var vaccinationCount = 0
        set(value) {
            checkCount(value, field)
            field = value
        }

    var waitingCount = 0
        set(value) {
            checkCount(value, field)
            field = value
        }

    init {
        TransferManager(mySim, this)
        ExaminationTransferProcess(mySim, this)
        VaccinationTransferProcess(mySim, this)
        WaitingTransferProcess(mySim, this)

        addOwnMessage(MessageCodes.examinationTransferStart)
        addOwnMessage(MessageCodes.examinationTransferEnd)
        addOwnMessage(MessageCodes.vaccinationTransferStart)
        addOwnMessage(MessageCodes.vaccinationTransferEnd)
        addOwnMessage(MessageCodes.waitingTransferStart)
        addOwnMessage(MessageCodes.waitingTransferEnd)

        addOwnMessage(MessageCodes.transferEnd)
    }

    override fun checkFinalState() {
        if (examinationCount > 0)
            throw IllegalStateException("There are $examinationCount patients in examination transfer.")
        if (vaccinationCount > 0)
            throw IllegalStateException("There are $vaccinationCount patients in vaccination transfer.")
        if (waitingCount > 0)
            throw IllegalStateException("There are $waitingCount patients in waiting transfer.")
    }

    private fun checkCount(value: Int, field: Int) {
        if (abs(value - field) > 1)
            throw IllegalArgumentException("You can only increment/decrement the value by 1.")
        if (value < 0)
            throw IllegalArgumentException("Count cannot by negative.")
    }

}