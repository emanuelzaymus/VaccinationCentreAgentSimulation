package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager

class ModelManager(mySim: Simulation, myAgent: Agent) : VaccinationCentreManager(Ids.modelManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.init -> noticeEnvironmentInit(message)

            MessageCodes.patientArrival -> requestRegistration(message)

            MessageCodes.registrationDone -> requestExamination(message)

            MessageCodes.examinationDone -> requestVaccination(message)

            MessageCodes.vaccinationDone -> noticeEnvironmentDone(message)
        }
    }

    private fun noticeEnvironmentInit(message: MessageForm) {
        debug("ModelManager - init")
        message.setAddressee(mySim().findAgent(Ids.environmentAgent))

        notice(message)
    }

    private fun requestRegistration(message: MessageForm) {
        debug("ModelManager - patientArrival")

        message.setCode(MessageCodes.registration)
        message.setAddressee(mySim().findAgent(Ids.registrationAgent))

        request(message)
    }

    private fun requestExamination(message: MessageForm) {
        debug("ModelManager - patientRegistrationDone")

        message.setCode(MessageCodes.examination)
        message.setAddressee(mySim().findAgent(Ids.examinationAgent))

        request(message)
    }

    private fun requestVaccination(message: MessageForm) {
        debug("ModelManager - examinationDone")

        message.setCode(MessageCodes.vaccination)
        message.setAddressee(mySim().findAgent(Ids.vaccinationAgent))

        request(message)
    }

    private fun noticeEnvironmentDone(message: MessageForm) {
        debug("ModelManager - patientRegistrationDone")

        message.setCode(MessageCodes.patientLeaving)
        message.setAddressee(mySim().findAgent(Ids.environmentAgent))

        notice(message)
    }

}