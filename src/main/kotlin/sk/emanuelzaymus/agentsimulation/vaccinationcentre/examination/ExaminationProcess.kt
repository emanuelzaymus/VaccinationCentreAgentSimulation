package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.EXAMINATION_DURATION_MEAN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class ExaminationProcess(mySim: Simulation, myAgent: ExaminationAgent) :
    Process(Ids.examinationProcess, mySim, myAgent) {

    companion object {
        private val registrationDuration = ExponentialRNG(EXAMINATION_DURATION_MEAN)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> startExamination(message)

            MessageCodes.examinationDone -> examinationDone(message)
        }
    }

    private fun startExamination(message: MessageForm) {
        debug("RegistrationProcess - start")
        message.setCode(MessageCodes.examinationDone)

        hold(registrationDuration.sample(), message)
    }

    private fun examinationDone(message: MessageForm) {
        debug("RegistrationProcess - registrationEnd")
        assistantFinished(message)
    }

}