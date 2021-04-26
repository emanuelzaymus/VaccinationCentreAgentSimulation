package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager

class RegistrationManager(mySim: Simulation, private val myAgent: RegistrationAgent) :
    VaccinationCentreManager(Ids.registrationManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.patientRegistration -> tryRegisterPatient(message as Message)

            IdList.finish -> patientRegistrationDone(message as Message)
        }
    }

    private fun tryRegisterPatient(message: Message) {
        debug("RegistrationManager - patientRegistration")

        message.patient.startWaiting()

        if (myAgent.workers.anyAvailable())
            startRegistration(message)
        else
            myAgent.queue.enqueue(message)
    }

    private fun patientRegistrationDone(message: Message) {
        debug("RegistrationManager - finish")

        message.administrativeWorker!!.isBusy = false
        message.administrativeWorker = null

        if (myAgent.queue.size > 0)
            startRegistration(myAgent.queue.dequeue())

        message.setCode(MessageCodes.patientRegistrationDone)
        response(message)
    }

    private fun startRegistration(message: Message) {
        message.patient.stopWaiting()
        myAgent.waitingTimeStat.addSample(message.patient.getWaitingTotal())

        message.administrativeWorker = myAgent.workers.getRandomAvailable().apply { isBusy = true }
        message.setAddressee(myAgent.findAssistant(Ids.registrationProcess))

        startContinualAssistant(message)
    }

}