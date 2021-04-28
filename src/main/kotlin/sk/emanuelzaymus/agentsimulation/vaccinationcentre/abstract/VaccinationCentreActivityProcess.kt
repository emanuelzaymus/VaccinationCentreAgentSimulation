package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class VaccinationCentreActivityProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val activityDoneMsgCode: Int

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {

            IdList.start -> startActivity(message)

            activityDoneMsgCode -> endActivity(message)
        }
    }

    private fun startActivity(message: MessageForm) {
        message.setCode(activityDoneMsgCode)

        hold(getDuration(), message)
    }

    private fun endActivity(message: MessageForm) = assistantFinished(message)

}