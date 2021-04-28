package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import OSPRNG.UniformContinuousRNG
import OSPRNG.UniformDiscreteRNG
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.NOT_ARRIVING_PATIENTS_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.WORKING_TIME

class PatientArrivalsScheduler(mySim: Simulation, myAgent: CommonAgent, private val numberOfPatients: Int) :
    Scheduler(Ids.patientArrivalsScheduler, mySim, myAgent), IReusable {

    companion object {
        private val notArrivingPatients = UniformContinuousRNG(NOT_ARRIVING_PATIENTS_MIN, NOT_ARRIVING_PATIENTS_MAX)
    }

    private val messagePool = Pool { Message(mySim) }

    private val arrivingPatientNumbers = UniformDiscreteRNG(0, numberOfPatients)
    private val eventDuration = WORKING_TIME / numberOfPatients
    private var numberOfNotArrivingPatients = (notArrivingPatients.sample() * (numberOfPatients / 540)).toInt()
    private var executedArrivals = 0

    override fun restart() {
        numberOfNotArrivingPatients = (notArrivingPatients.sample() * (numberOfPatients / 540)).toInt()
        executedArrivals = 0
    }

    override fun processMessage(message: MessageForm) {
        debug("PatientArrivalsScheduler", message)

        when (message.code()) {

            IdList.start -> startScheduling(message)

            MessageCodes.scheduleArrival -> scheduleArrival(message)

            MessageCodes.patientLeaving -> returnPatient(message)
        }
    }

    private fun startScheduling(message: MessageForm) {
        message.setCode(MessageCodes.scheduleArrival)

        hold(.0, message) // To deliver immediately
    }

    private fun scheduleArrival(message: MessageForm) {
        scheduleNextPatientArrival(message)

        assistantFinished(messagePool.acquire())
    }

    private fun scheduleNextPatientArrival(message: MessageForm) {
        var duration = eventDuration

        while (true) {
            if (executedArrivals >= numberOfPatients) {
                break
            }
            executedArrivals++

            if (numberOfNotArrivingPatients < arrivingPatientNumbers.sample()) {
                hold(duration, message)
                break
            } else {
                duration += eventDuration
            }
        }
    }

    private fun returnPatient(message: MessageForm) {
        messagePool.release(message as Message)
    }

    override fun checkFinalState() {
        if (executedArrivals != numberOfPatients)
            throw Exception("Required count of patient ($numberOfPatients) was not generated. Only $executedArrivals")
    }

}