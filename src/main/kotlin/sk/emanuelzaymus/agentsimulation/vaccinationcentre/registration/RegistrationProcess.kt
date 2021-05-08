package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class RegistrationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.registrationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = UniformContinuousRNG(C.REGISTRATION_DURATION_MIN, C.REGISTRATION_DURATION_MAX)
    }

    override val debugName = "RegistrationProcess"
    override val processEndMsgCode = MessageCodes.registrationEnd

    override fun getDuration(): Double = registrationDuration.sample()

}