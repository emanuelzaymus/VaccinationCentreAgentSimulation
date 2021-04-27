package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager

class EnvironmentManager(mySim: Simulation, myAgent: Agent) :
    VaccinationCentreManager(Ids.environmentManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        debug("EnvironmentManager", message)

        when (message.code()) {

            MessageCodes.init -> startPatientScheduling(message)

            // PatientArrivalScheduler - patient generated
            IdList.finish -> noticeModelPatientArrival(message)

            MessageCodes.patientLeaving -> noticeSchedulerPatientLeaving(message)
        }
    }

    private fun startPatientScheduling(message: MessageForm) {
        message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))

        startContinualAssistant(message)
    }

    private fun noticeModelPatientArrival(message: MessageForm) {
        message.setCode(MessageCodes.patientArrival)
        message.setAddressee(mySim().findAgent(Ids.modelAgent))

        notice(message)
    }

    private fun noticeSchedulerPatientLeaving(message: MessageForm) {
        message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))

        notice(message)
    }

}