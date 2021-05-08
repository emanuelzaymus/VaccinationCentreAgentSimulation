package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.examination

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.EXAMINATION_DURATION_MEAN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityProcess

class ExaminationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.examinationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = ExponentialRNG(EXAMINATION_DURATION_MEAN)
    }

    override val debugName = "ExaminationProcess"
    override val processEndMsgCode = MessageCodes.examinationEnd

    override fun getDuration(): Double = registrationDuration.sample()

}