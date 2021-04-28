package sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting

import OSPABA.*
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*

class WaitingProcess(mySim: Simulation, myAgent: CommonAgent) :
    Process(Ids.waitingProcess, mySim, myAgent) {

    companion object {
        private val waitingDuration = UniformContinuousRNG(.0, 1.0)
    }

    override fun processMessage(message: MessageForm) {
        debug("WaitingProcess", message)

        when (message.code()) {

            IdList.start -> startActivity(message)

            MessageCodes.waitingEnd -> endActivity(message)
        }
    }

    private fun startActivity(message: MessageForm) {
        message.setCode(MessageCodes.waitingEnd)

        if (waitingDuration.sample() < WAITING_LESS_PROBABILITY)
            hold(WAITING_DURATION_LESS, message)
        else
            hold(WAITING_DURATION_MORE, message)
    }

    private fun endActivity(message: MessageForm) = assistantFinished(message)

}