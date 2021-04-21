package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class RegistrationProcess(id: Int = Ids.registrationProcess, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    companion object {
        private val registrationDuration = ExponentialRNG(4.0)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> {
                message.setCode(MessageCodes.registrationEnd)

                hold(registrationDuration.sample(), message)
            }
            
            MessageCodes.registrationEnd -> assistantFinished(message)
        }
    }

}