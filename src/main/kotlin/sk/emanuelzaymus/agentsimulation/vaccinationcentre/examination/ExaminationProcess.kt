package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class ExaminationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.examinationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = ExponentialRNG(C.EXAMINATION_DURATION_MEAN)
    }

    override val debugName = "ExaminationProcess"
    override val processEndMsgCode = MessageCodes.examinationEnd

    override fun getDuration(): Double = registrationDuration.sample()

}