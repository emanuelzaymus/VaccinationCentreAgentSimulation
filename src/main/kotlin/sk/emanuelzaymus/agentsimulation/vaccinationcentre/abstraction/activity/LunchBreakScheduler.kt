package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class LunchBreakScheduler(id: Int, mySim: Simulation, myAgent: CommonAgent) : Scheduler(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val scheduleAt: Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            IdList.start -> scheduleLunchBreak(message)

            MessageCodes.lunchBreakNow -> lunchBreakNow(message)
        }
    }

    private fun scheduleLunchBreak(message: MessageForm) {
        message.setCode(MessageCodes.lunchBreakNow)

        hold(scheduleAt, message)
    }

    private fun lunchBreakNow(message: MessageForm) = assistantFinished(message)

}