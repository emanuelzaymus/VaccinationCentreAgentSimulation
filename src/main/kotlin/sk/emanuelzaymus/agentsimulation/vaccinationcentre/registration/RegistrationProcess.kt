package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import OSPRNG.ExponentialRNG
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.REGISTRATION_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.REGISTRATION_DURATION_MIN

class RegistrationProcess(mySim: Simulation, myAgent: CommonAgent) :
    Process(Ids.registrationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = UniformContinuousRNG(REGISTRATION_DURATION_MIN, REGISTRATION_DURATION_MAX)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> startRegistration(message)

            MessageCodes.registrationEnd -> endRegistration(message)
        }
    }

    private fun startRegistration(message: MessageForm) {
        debug("RegistrationProcess - start")
        message.setCode(MessageCodes.registrationEnd)

        hold(registrationDuration.sample(), message)
    }

    private fun endRegistration(message: MessageForm) {
        debug("RegistrationProcess - registrationEnd")
        assistantFinished(message)
    }

}