package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class VaccinationCentreProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val processEndMsgCode: Int

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            IdList.start -> startProcess(message)

            processEndMsgCode -> endProcess(message)
        }
    }

    protected open fun startProcess(message: MessageForm) {
        message.setCode(processEndMsgCode)
        hold(getDuration(), message)
    }

    protected open fun endProcess(message: MessageForm) = assistantFinished(message)

}