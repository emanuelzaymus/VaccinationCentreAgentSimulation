package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager

class ModelManager(mySim: Simulation, myAgent: Agent) : VaccinationCentreManager(Ids.modelManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("ModelManager", message)

        when (message.code()) {
            MessageCodes.init -> noticeAgentsInit(message)

            MessageCodes.patientArrival -> requestRegistration(message)

            MessageCodes.registrationEnd -> requestExaminationTransfer(message)

            MessageCodes.examinationTransferEnd -> requestExamination(message)

            MessageCodes.examinationEnd -> requestVaccinationTransfer(message)

            MessageCodes.vaccinationTransferEnd -> requestVaccination(message)

            MessageCodes.vaccinationEnd -> requestWaitingTransfer(message)

            MessageCodes.waitingTransferEnd -> requestWaiting(message)

            MessageCodes.waitingEnd -> noticeEnvironmentDone(message)

            MessageCodes.lunchBreakStart -> requestLunchBreak(message)

            MessageCodes.lunchBreakEnd -> lunchBreakDone(message)
        }
    }

    private fun noticeAgentsInit(message: MessageForm) {
        notice(message.createCopy().apply { setAddressee(Ids.registrationAgent) })
        notice(message.createCopy().apply { setAddressee(Ids.examinationAgent) })
        notice(message.createCopy().apply { setAddressee(Ids.vaccinationAgent) })

        message.setAddressee(Ids.environmentAgent)
        notice(message)
    }

    private fun requestRegistration(message: MessageForm) =
        sendRequest(MessageCodes.registrationStart, Ids.registrationAgent, message)

    private fun requestExaminationTransfer(message: MessageForm) =
        sendRequest(MessageCodes.examinationTransferStart, Ids.transferAgent, message)

    private fun requestExamination(message: MessageForm) =
        sendRequest(MessageCodes.examinationStart, Ids.examinationAgent, message)

    private fun requestVaccinationTransfer(message: MessageForm) =
        sendRequest(MessageCodes.vaccinationTransferStart, Ids.transferAgent, message)

    private fun requestVaccination(message: MessageForm) =
        sendRequest(MessageCodes.vaccinationStart, Ids.vaccinationAgent, message)

    private fun requestWaitingTransfer(message: MessageForm) =
        sendRequest(MessageCodes.waitingTransferStart, Ids.transferAgent, message)

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

    private fun requestLunchBreak(message: MessageForm) {
        message.setAddressee(Ids.lunchBreakAgent)
        request(message)
    }

    private fun lunchBreakDone(message: MessageForm) = response(message)

}