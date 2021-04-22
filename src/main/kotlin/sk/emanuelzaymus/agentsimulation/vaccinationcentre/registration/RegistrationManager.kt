package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.PatientMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class RegistrationManager(id: Int = Ids.registrationManager, mySim: Simulation, myAgent: Agent) :
    Manager(id, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.patientRegistration -> registerPatient(message)

            IdList.finish -> patientRegistrationDone(message)
        }
    }

    private fun registerPatient(message: MessageForm) {
        debug("RegistrationManager - patientRegistration")

        if (myAgent().isWorking) {
            (message as PatientMessage).patient.waitingStart = mySim().currentTime()

            myAgent().patientQueue.enqueue(message)
        } else {
            startWork(message)
        }
    }

    private fun patientRegistrationDone(message: MessageForm) {
        debug("RegistrationManager - finish")

        myAgent().isWorking = false
        myAgent().waitingTimeStat.addSample((message as PatientMessage).patient.waitingTotal)

        if (myAgent().patientQueue.size > 0) {
            val patientMessage = myAgent().patientQueue.dequeue() as PatientMessage

            patientMessage.patient.waitingTotal = mySim().currentTime() - patientMessage.patient.waitingStart
            startWork(patientMessage)
        }

        message.setCode(MessageCodes.patientRegistrationDone)
        response(message)
    }

    private fun startWork(message: MessageForm) {
        myAgent().isWorking = true
        message.setAddressee(myAgent().findAssistant(Ids.registrationProcess))

        startContinualAssistant(message)
    }

    override fun myAgent(): RegistrationAgent = super.myAgent() as RegistrationAgent

}