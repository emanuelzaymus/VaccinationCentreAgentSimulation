package sk.emanuelzaymus.agentsimulation.vaccinationcentre.workersbreak

import OSPABA.*
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*

class LunchProcess(mySim: Simulation, myAgent: CommonAgent) :
    Process(Ids.lunchProcess, mySim, myAgent) {

    companion object {
        private val lunchDuration = TriangularRNG(LUNCH_DURATION_MIN, LUNCH_DURATION_MODE, LUNCH_DURATION_MAX)
    }

    override fun processMessage(message: MessageForm) {
        debug("LunchProcess", message)

        when (message.code()) {
            // Manager - start activity
            IdList.start -> startLunch(message)

            MessageCodes.lunchEnd -> lunchDone(message)
        }
    }

    private fun startLunch(message: MessageForm) {
        message.setCode(MessageCodes.lunchEnd)

        hold(getDuration(), message)
    }

    private fun lunchDone(message: MessageForm) = assistantFinished(message)

    private fun getDuration(): Double = lunchDuration.sample()

}