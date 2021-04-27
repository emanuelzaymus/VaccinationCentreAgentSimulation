package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.utils.debug

abstract class VaccinationCentreActivityProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val activityDoneMsgCode: Int

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> startActivity(message)

            activityDoneMsgCode -> endActivity(message)
        }
    }

    private fun startActivity(message: MessageForm) {
        debug(debugName, message)
        message.setCode(activityDoneMsgCode)

        hold(getDuration(), message)
    }

    private fun endActivity(message: MessageForm) {
        debug(debugName, message)
        assistantFinished(message)
    }

}