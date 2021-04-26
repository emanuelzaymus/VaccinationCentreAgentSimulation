package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.PatientMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class RegistrationManager(mySim: Simulation, private val myAgent: RegistrationAgent) :
    Manager(Ids.registrationManager, mySim, myAgent), IReusable {

    private val workers = BusyList(1) { AdministrativeWorker() }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.patientRegistration -> tryRegisterPatient(message as PatientMessage)

            IdList.finish -> patientRegistrationDone(message as PatientMessage)
        }
    }

    private fun tryRegisterPatient(message: PatientMessage) {
        debug("RegistrationManager - patientRegistration")

        message.patient.startWaiting()

        if (workers.anyAvailable())
            startRegistration(message)
        else
            myAgent.patientQueue.enqueue(message)
    }

    private fun patientRegistrationDone(message: PatientMessage) {
        debug("RegistrationManager - finish")

        message.administrativeWorker!!.isBusy = false
        message.administrativeWorker = null

        if (myAgent.patientQueue.size > 0)
            startRegistration(myAgent.patientQueue.dequeue())

        message.setCode(MessageCodes.patientRegistrationDone)
        response(message)
    }

    private fun startRegistration(message: PatientMessage) {
        message.patient.stopWaiting()
        myAgent.waitingTimeStat.addSample(message.patient.getWaitingTotal())

        message.administrativeWorker = workers.getRandomAvailable().apply { isBusy = true }
        message.setAddressee(myAgent.findAssistant(Ids.registrationProcess))

        startContinualAssistant(message)
    }

    override fun checkFinalState() = workers.checkFinalState()

    override fun restart() = workers.restart()

}