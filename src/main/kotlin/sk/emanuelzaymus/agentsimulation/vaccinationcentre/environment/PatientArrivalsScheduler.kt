package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.PatientMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class PatientArrivalsScheduler(id: Int = Ids.patientArrivalsScheduler, mySim: Simulation, myAgent: CommonAgent) :
    Scheduler(id, mySim, myAgent) {

    private val patientMessagePool = Pool { PatientMessage(mySim) }

    companion object {
        private val arrivalsGenerator = ExponentialRNG(5.0)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> {
                debug("PatientArrivalsScheduler - start")
                message.setCode(MessageCodes.newPatient)

//                hold(.0, message); // To deliver immediately
                hold(arrivalsGenerator.sample(), message)
            }

            MessageCodes.newPatient -> {
                debug("PatientArrivalsScheduler - newPatient")
                val newPatientMessage = patientMessagePool.acquire()

                // hold(60.0, copy)
                hold(arrivalsGenerator.sample(), message)
                assistantFinished(newPatientMessage)
            }

            MessageCodes.patientLeaving -> {
                debug("PatientArrivalsScheduler - patientLeaving")
                patientMessagePool.release(message as PatientMessage)
            }
        }
    }

}