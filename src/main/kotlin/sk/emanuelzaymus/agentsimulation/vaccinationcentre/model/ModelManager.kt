package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager

class ModelManager(mySim: Simulation, myAgent: Agent) : VaccinationCentreManager(Ids.modelManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("ModelManager", message)

        when (message.code()) {

            MessageCodes.init -> noticeAgentsInit(message)

            MessageCodes.patientArrival -> requestRegistration(message)

            MessageCodes.registrationEnd -> startExaminationTransfer(message)

            MessageCodes.examinationEnd -> startVaccinationTransfer(message)

            MessageCodes.vaccinationEnd -> startWaitingTransfer(message)

            MessageCodes.waitingEnd -> noticeEnvironmentDone(message)

            IdList.finish -> when (message.sender().id()) {
                Ids.examinationTransferProcess -> requestExamination(message)

                Ids.vaccinationTransferProcess -> requestVaccination(message)

                Ids.waitingTransferProcess -> requestWaiting(message)
            }
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

    private fun startExaminationTransfer(message: MessageForm) =
        startTransfer(Ids.examinationTransferProcess, message)

    private fun requestExamination(message: MessageForm) =
        sendRequest(MessageCodes.examinationStart, Ids.examinationAgent, message)

    private fun startVaccinationTransfer(message: MessageForm) =
        startTransfer(Ids.vaccinationTransferProcess, message)

    private fun requestVaccination(message: MessageForm) =
        sendRequest(MessageCodes.vaccinationStart, Ids.vaccinationAgent, message)

    private fun startWaitingTransfer(message: MessageForm) =
        startTransfer(Ids.waitingTransferProcess, message)

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

    private fun startTransfer(addresseeId: Int, message: MessageForm) {
        message.setAddressee(addresseeId)

        startContinualAssistant(message)
    }

}