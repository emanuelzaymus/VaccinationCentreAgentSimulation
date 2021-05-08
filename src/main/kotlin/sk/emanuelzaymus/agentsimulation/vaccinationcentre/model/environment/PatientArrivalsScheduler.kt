package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment.arrivaltimesgenerators.ArrivalTimesGenerator
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment.arrivaltimesgenerators.EarlyArrivalTimesGenerator
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment.arrivaltimesgenerators.ExactArrivalTimesGenerator

class PatientArrivalsScheduler(
    private val mySim: Simulation, myAgent: CommonAgent, numberOfPatients: Int, earlyArrivals: Boolean
) : Scheduler(Ids.patientArrivalsScheduler, mySim, myAgent), IReusable {

    private val messagePool = Pool { Message(mySim) }

    private val arrivalTimes =
        ArrivalTimes(numberOfPatients, if (earlyArrivals) EarlyArrivalTimesGenerator else ExactArrivalTimesGenerator)

    private var returnedPatients = 0

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

        scheduleNextPatientArrival(message)
    }

    private fun scheduleArrival(message: MessageForm) {
        scheduleNextPatientArrival(message)

        assistantFinished(messagePool.acquire())
    }

    private fun scheduleNextPatientArrival(message: MessageForm) {
        if (!arrivalTimes.isEnd())
            hold(arrivalTimes.nextBetweenArrivalsDuration(), message)
    }

    private fun returnPatient(message: MessageForm) {
        messagePool.release(message as Message)

        if (++returnedPatients >= arrivalTimes.arrivingPatients)
            mySim.stopReplication()
    }

    override fun restart() {
        returnedPatients = 0
        arrivalTimes.restart()
    }

    override fun checkFinalState() {
        if (returnedPatients != arrivalTimes.arrivingPatients)
            throw IllegalStateException("Not all patients (${arrivalTimes.arrivingPatients}) have returned. Only $returnedPatients")

        arrivalTimes.checkFinalState()
    }


    private class ArrivalTimes(val numberOfPatients: Int, val generator: ArrivalTimesGenerator) : IReusable {

        private var arrivalTimes: List<Double> = generator.generateArrivalTimes(numberOfPatients)
        private var index = 0
        val arrivingPatients: Int get() = arrivalTimes.size

        fun nextBetweenArrivalsDuration(): Double {
            val ret: Double =
                if (index <= 0) arrivalTimes[index]
                else arrivalTimes[index] - arrivalTimes[index - 1]

            index++
            return ret
        }

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