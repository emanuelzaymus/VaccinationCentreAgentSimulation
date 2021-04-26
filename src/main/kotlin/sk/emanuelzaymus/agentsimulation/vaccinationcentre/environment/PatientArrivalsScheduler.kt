package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class PatientArrivalsScheduler(mySim: Simulation, myAgent: CommonAgent, numberOfPatients: Int) :
    Scheduler(Ids.patientArrivalsScheduler, mySim, myAgent), IReusable {

    private val messagePool = Pool { Message(mySim) }
    private val requiredCount = numberOfPatients
    private var generatedCount = 0

    companion object {
        private val arrivalsGenerator = ExponentialRNG(5.0)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> startScheduling(message)

            MessageCodes.getNewPatient -> getNewPatient(message)

            MessageCodes.patientLeaving -> returnPatient(message)
        }
    }

    private fun startScheduling(message: MessageForm) {
        debug("PatientArrivalsScheduler - start")
        message.setCode(MessageCodes.getNewPatient)

        // hold(.0, message); // To deliver immediately
        hold(arrivalsGenerator.sample(), message)
    }

    private fun getNewPatient(message: MessageForm) {
        debug("PatientArrivalsScheduler - newPatient")
        val newMessage = messagePool.acquire()

        if (++generatedCount < requiredCount) {
            // hold(60.0, copy)
            hold(arrivalsGenerator.sample(), message)
        }
        assistantFinished(newMessage)
    }

    private fun returnPatient(message: MessageForm) {
        debug("PatientArrivalsScheduler - patientLeaving")
        messagePool.release(message as Message)
    }

    override fun restart() {
        generatedCount = 0
    }

    override fun checkFinalState() {
        if (generatedCount != requiredCount)
            throw Exception("Required count of patient ($requiredCount) was not generated. Only $generatedCount")
    }

}