package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class EnvironmentManager(mySim: Simulation, myAgent: Agent) : Manager(Ids.environmentManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.init -> startPatientScheduling(message)

            IdList.finish -> noticeModelPatientArrival(message)

            MessageCodes.patientLeaving -> noticeSchedulerPatientLeaving(message)
        }
    }

    private fun startPatientScheduling(message: MessageForm) {
        debug("EnvironmentManager - init")
        message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))

        startContinualAssistant(message)
    }

    private fun noticeModelPatientArrival(message: MessageForm) {
        debug("EnvironmentManager - finish")

        message.setCode(MessageCodes.patientArrival)
        message.setAddressee(mySim().findAgent(Ids.modelAgent))

        notice(message)
    }

    private fun noticeSchedulerPatientLeaving(message: MessageForm) {
        debug("EnvironmentManager - patientLeaving")
        message.setAddressee(myAgent().findAssistant(Ids.patientArrivalsScheduler))

        notice(message)
    }

}