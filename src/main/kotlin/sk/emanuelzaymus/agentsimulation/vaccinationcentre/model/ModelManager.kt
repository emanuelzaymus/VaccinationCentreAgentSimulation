package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager

class ModelManager(mySim: Simulation, myAgent: Agent) : VaccinationCentreManager(Ids.modelManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("ModelManager", message)

        when (message.code()) {

            MessageCodes.init -> noticeEnvironmentInit(message)

            MessageCodes.patientArrival -> requestRegistration(message)

            MessageCodes.registrationEnd -> startExaminationTransfer(message)

            MessageCodes.examinationTransferEnd -> requestExamination(message)

            MessageCodes.examinationEnd -> startVaccinationTransfer(message)

            MessageCodes.vaccinationTransferEnd -> requestVaccination(message)

            MessageCodes.vaccinationEnd -> startWaitingTransfer(message)

            MessageCodes.waitingTransferEnd -> requestWaiting(message)

            MessageCodes.waitingEnd -> noticeEnvironmentDone(message)
        }
    }


    private fun noticeEnvironmentInit(message: MessageForm) {
        message.setAddressee(Ids.environmentAgent)
        notice(message)
    }

    private fun requestRegistration(message: MessageForm) =
        sendRequest(MessageCodes.registrationStart, Ids.registrationAgent, message)

    private fun startExaminationTransfer(message: MessageForm) =
        sendNotice(MessageCodes.examinationTransferStart, Ids.examinationTransferProcess, message)

    private fun requestExamination(message: MessageForm) =
        sendRequest(MessageCodes.examinationStart, Ids.examinationAgent, message)

    private fun startVaccinationTransfer(message: MessageForm) =
        sendNotice(MessageCodes.vaccinationTransferStart, Ids.vaccinationTransferProcess, message)

    private fun requestVaccination(message: MessageForm) =
        sendRequest(MessageCodes.vaccinationStart, Ids.vaccinationAgent, message)

    private fun startWaitingTransfer(message: MessageForm) =
        sendNotice(MessageCodes.waitingTransferStart, Ids.waitingTransferProcess, message)

    private fun requestWaiting(message: MessageForm) =
        sendRequest(MessageCodes.waitingStart, Ids.waitingAgent, message)

    private fun noticeEnvironmentDone(message: MessageForm) {
        message.setCode(MessageCodes.patientLeaving)
        message.setAddressee(Ids.environmentAgent)

        notice(message)
    }

    private fun sendRequest(msgCode: Int, addresseeId: Int, message: MessageForm) {
        message.setCode(msgCode)
        message.setAddressee(addresseeId)

        request(message)
    }

    private fun sendNotice(msgCode: Int, addresseeId: Int, message: MessageForm) {
        message.setCode(msgCode)
        message.setAddressee(addresseeId)

        notice(message)
    }

}