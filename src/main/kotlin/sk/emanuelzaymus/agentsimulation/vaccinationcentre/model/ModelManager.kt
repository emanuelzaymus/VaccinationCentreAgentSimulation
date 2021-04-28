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

            MessageCodes.registrationDone -> requestExamination(message)

            MessageCodes.examinationDone -> requestVaccination(message)

            MessageCodes.vaccinationDone -> requestWaiting(message)

            MessageCodes.waitingDone -> noticeEnvironmentDone(message)
        }
    }

    private fun noticeEnvironmentInit(message: MessageForm) {
        message.setAddressee(Ids.environmentAgent)
        notice(message)
    }

    private fun requestRegistration(message: MessageForm) =
        sendRequest(MessageCodes.registration, Ids.registrationAgent, message)

    private fun requestExamination(message: MessageForm) =
        sendRequest(MessageCodes.examination, Ids.examinationAgent, message)

    private fun requestVaccination(message: MessageForm) =
        sendRequest(MessageCodes.vaccination, Ids.vaccinationAgent, message)

    private fun requestWaiting(message: MessageForm) =
        sendRequest(MessageCodes.waiting, Ids.waitingAgent, message)

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

}