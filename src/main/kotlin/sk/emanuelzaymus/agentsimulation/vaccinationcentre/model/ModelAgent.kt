package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.InitMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transferprocesses.ExaminationTransferProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transferprocesses.VaccinationTransferProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.transferprocesses.WaitingTransferProcess

class ModelAgent(mySim: Simulation) : VaccinationCentreAgent(Ids.modelAgent, mySim, null) {

    init {
        ModelManager(mySim, this)
        ExaminationTransferProcess(mySim, this)
        VaccinationTransferProcess(mySim, this)
        WaitingTransferProcess(mySim, this)

        addOwnMessage(MessageCodes.init)

        addOwnMessage(MessageCodes.patientArrival)
        addOwnMessage(MessageCodes.registrationEnd)
        addOwnMessage(MessageCodes.examinationEnd)
        addOwnMessage(MessageCodes.vaccinationEnd)
        addOwnMessage(MessageCodes.waitingEnd)

        addOwnMessage(MessageCodes.transferEnd)
    }

    fun runSimulation() {
        val message = InitMessage(mySim())
        message.setAddressee(this)

        manager().notice(message)
    }

}