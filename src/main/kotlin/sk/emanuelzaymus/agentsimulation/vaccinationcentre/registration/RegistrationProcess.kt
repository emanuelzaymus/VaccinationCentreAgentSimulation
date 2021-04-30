package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.REGISTRATION_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.REGISTRATION_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityProcess

class RegistrationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.registrationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = UniformContinuousRNG(REGISTRATION_DURATION_MIN, REGISTRATION_DURATION_MAX)
    }

    override val debugName = "RegistrationProcess"
    override val activityEndMsgCode = MessageCodes.registrationEnd

    override fun getDuration(): Double = registrationDuration.sample()

}