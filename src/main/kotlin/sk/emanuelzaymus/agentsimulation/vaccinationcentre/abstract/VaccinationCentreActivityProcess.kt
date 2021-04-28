package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class VaccinationCentreActivityProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val activityEndMsgCode: Int

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            // Manager - start activity
            IdList.start -> startActivity(message)

            activityEndMsgCode -> endActivity(message)
        }
    }

    private fun startActivity(message: MessageForm) {
        message.setCode(activityEndMsgCode)

        hold(getDuration(), message)
    }

    private fun endActivity(message: MessageForm) = assistantFinished(message)

}