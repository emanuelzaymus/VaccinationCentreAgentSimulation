package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.arrivaltimesgenerators.ArrivalTimesGenerator
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.arrivaltimesgenerators.EarlyArrivalTimesGenerator
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.arrivaltimesgenerators.ExactArrivalTimesGenerator

class PatientArrivalsScheduler(mySim: Simulation, myAgent: CommonAgent, numberOfPatients: Int, earlyArrivals: Boolean) :
    Scheduler(Ids.patientArrivalsScheduler, mySim, myAgent), IReusable {

    private val messagePool = Pool { Message(mySim) }

    private val arrivalTimes =
        ArrivalTimes(numberOfPatients, if (earlyArrivals) EarlyArrivalTimesGenerator else ExactArrivalTimesGenerator)

    override fun processMessage(message: MessageForm) {
        debug("PatientArrivalsScheduler", message)

        when (message.code()) {
            // EnvironmentManager - start scheduling patients
            IdList.start -> startScheduling(message)

            MessageCodes.scheduleArrival -> scheduleArrival(message)

            MessageCodes.patientLeaving -> returnPatient(message)
        }
    }

    private fun startScheduling(message: MessageForm) {
        message.setCode(MessageCodes.scheduleArrival)
        // Deliver immediately
        hold(.0, message)
    }

    private fun scheduleArrival(message: MessageForm) {
        scheduleNextPatientArrival(message)

        assistantFinished(messagePool.acquire())
    }

    private fun scheduleNextPatientArrival(message: MessageForm) {
        if (!arrivalTimes.isEnd())
            hold(arrivalTimes.nextArrival(), message)
    }

    private fun returnPatient(message: MessageForm) = messagePool.release(message as Message)

    override fun restart() = arrivalTimes.restart()

    override fun checkFinalState() = arrivalTimes.checkFinalState()


    private class ArrivalTimes(val numberOfPatients: Int, val generator: ArrivalTimesGenerator) : IReusable {

        private var arrivalTimes = generator.generateArrivalTimes(numberOfPatients)
        private var index = 0

        fun nextArrival(): Double = arrivalTimes[index++]

        fun isEnd() = index >= arrivalTimes.size

        override fun restart() {
            arrivalTimes = generator.generateArrivalTimes(numberOfPatients)
            index = 0
        }

        override fun checkFinalState() {
            if (!isEnd())
                throw Exception("Required count of patient (${arrivalTimes.size}) was not processed, only $index")
        }
    }

}