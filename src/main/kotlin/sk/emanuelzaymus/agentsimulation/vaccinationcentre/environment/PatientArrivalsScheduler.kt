package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class PatientArrivalsScheduler(id: Int = Ids.patientArrivalsScheduler, mySim: Simulation, myAgent: CommonAgent) :
    Scheduler(id, mySim, myAgent) {

    companion object {
        private val arrivalsGenerator = ExponentialRNG(5.0)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> {
                message.setCode(MessageCodes.newPatient)

                hold(arrivalsGenerator.sample(), message)
            }

            MessageCodes.newPatient -> {
                val copy: MessageForm = message.createCopy()

                hold(arrivalsGenerator.sample(), copy)
                assistantFinished(message)
            }
        }
    }

}