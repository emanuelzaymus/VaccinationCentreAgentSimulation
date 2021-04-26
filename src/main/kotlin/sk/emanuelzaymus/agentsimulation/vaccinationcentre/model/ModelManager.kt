package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model

import OSPABA.Agent
import OSPABA.Manager
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

            MessageCodes.patientArrival -> requestPatientRegistration(message)

            MessageCodes.patientRegistrationDone -> noticeEnvironmentRegistrationDone(message)
        }
    }

    private fun noticeEnvironmentInit(message: MessageForm) {
        debug("ModelManager - init")
        message.setAddressee(mySim().findAgent(Ids.environmentAgent))

        notice(message)
    }

    private fun requestPatientRegistration(message: MessageForm) {
        debug("ModelManager - patientArrival")

        message.setCode(MessageCodes.patientRegistration)
        message.setAddressee(mySim().findAgent(Ids.registrationAgent))

        request(message)
    }

    private fun noticeEnvironmentRegistrationDone(message: MessageForm) {
        debug("ModelManager - patientRegistrationDone")

        message.setCode(MessageCodes.patientLeaving)
        message.setAddressee(mySim().findAgent(Ids.environmentAgent))

        notice(message)
    }

}