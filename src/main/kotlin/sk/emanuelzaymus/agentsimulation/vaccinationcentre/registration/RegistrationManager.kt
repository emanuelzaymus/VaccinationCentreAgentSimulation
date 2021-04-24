package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.busylist.BusyList
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.PatientMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class RegistrationManager(mySim: Simulation, myAgent: Agent) : Manager(Ids.registrationManager, mySim, myAgent),
    IReusable {

    private val myAgent = myAgent() as RegistrationAgent
    private val mySim = mySim()
    private val workers = BusyList(1) { AdministrativeWorker() }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.patientRegistration -> registerPatient(message as PatientMessage)

            IdList.finish -> patientRegistrationDone(message as PatientMessage)
        }
    }

    private fun registerPatient(message: PatientMessage) {
        debug("RegistrationManager - patientRegistration")

        if (workers.anyAvailable()) {
            message.administrativeWorker = workers.getRandomAvailable()
            startWork(message)

        } else {
            message.patient.waitingStart = mySim.currentTime()
            myAgent.patientQueue.enqueue(message)
        }
    }

    private fun patientRegistrationDone(message: PatientMessage) {
        debug("RegistrationManager - finish")

        message.administrativeWorker!!.isBusy = false
        message.administrativeWorker = null
//        myAgent().isWorking = false

        myAgent.waitingTimeStat.addSample(message.patient.waitingTotal)

        if (myAgent.patientQueue.size > 0) {
            val patientMessage: PatientMessage = myAgent.patientQueue.dequeue()

            // todo: make startWaiting(), stopWaiting() methods
            patientMessage.patient.waitingTotal = mySim.currentTime() - patientMessage.patient.waitingStart
            patientMessage.administrativeWorker = workers.getRandomAvailable()
            startWork(patientMessage)
        }

        message.setCode(MessageCodes.patientRegistrationDone)
        response(message)
    }

    private fun startWork(message: PatientMessage) {
        message.administrativeWorker!!.isBusy = true
//        myAgent().isWorking = true
        message.setAddressee(myAgent.findAssistant(Ids.registrationProcess))

        startContinualAssistant(message)
    }

    override fun checkFinalState() = workers.checkFinalState()

    override fun restart() = workers.restart()

}