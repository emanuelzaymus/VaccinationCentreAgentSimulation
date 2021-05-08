package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.registration

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.REGISTRATION_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.REGISTRATION_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityProcess

class RegistrationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.registrationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = UniformContinuousRNG(REGISTRATION_DURATION_MIN, REGISTRATION_DURATION_MAX)
    }

    override val debugName = "RegistrationProcess"
    override val processEndMsgCode = MessageCodes.registrationEnd

    override fun getDuration(): Double = registrationDuration.sample()

}