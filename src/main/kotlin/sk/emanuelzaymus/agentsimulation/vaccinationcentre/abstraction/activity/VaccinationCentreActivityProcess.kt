package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
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
            IdList.start -> startActivity(message as Message)

            activityEndMsgCode -> endActivity(message as Message)
        }
    }

    protected open fun startActivity(message: Message) {
        message.worker!!.state = WorkerState.WORKING
        message.worker!!.isBusy = true
        message.setCode(activityEndMsgCode)

        hold(getDuration(), message)
    }

    private fun endActivity(message: Message) {
        message.worker!!.state = WorkerState.FREE
        message.worker!!.isBusy = false

        assistantFinished(message)
    }

}