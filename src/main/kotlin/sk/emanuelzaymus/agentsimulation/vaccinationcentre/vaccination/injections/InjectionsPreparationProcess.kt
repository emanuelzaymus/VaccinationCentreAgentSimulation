package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.*
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*

class InjectionsPreparationProcess(mySim: Simulation, myAgent: CommonAgent) :
    Process(Ids.injectionsPreparationProcess, mySim, myAgent) {

    companion object {
        private val preparationDuration =
            TriangularRNG(INJECTION_PREP_DURATION_MIN, INJECTION_PREP_DURATION_MODE, INJECTION_PREP_DURATION_MAX)
    }

    override fun processMessage(message: MessageForm) {
        debug("InjectionsPreparationProcess", message)

        when (message.code()) {
            // Manager - start activity
            IdList.start -> startActivity(message as InjectionsPreparationMessage)

            MessageCodes.injectionsPreparationEnd -> endActivity(message as InjectionsPreparationMessage)
        }
    }

    private fun startActivity(message: InjectionsPreparationMessage) {
        message.nurse!!.isPreparing = true
        message.setCode(MessageCodes.injectionsPreparationEnd)

        hold(getDuration(), message)
    }

    private fun endActivity(message: InjectionsPreparationMessage) {
        message.nurse!!.restartInjectionLeft()
        message.nurse!!.isPreparing = false

        assistantFinished(message)
    }

    private fun getDuration(): Double = List(INJECTIONS_COUNT_TO_PREPARE) { preparationDuration.sample() }.sum()

}
