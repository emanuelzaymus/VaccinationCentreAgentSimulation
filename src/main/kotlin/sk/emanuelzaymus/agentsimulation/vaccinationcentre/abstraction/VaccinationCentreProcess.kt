package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug

abstract class VaccinationCentreProcess<T : MessageForm>(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val processEndMsgCode: Int

    protected abstract fun getDuration(): Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            IdList.start -> {
                @Suppress("UNCHECKED_CAST")
                startProcess(message as T)
            }

            processEndMsgCode -> {
                @Suppress("UNCHECKED_CAST")
                endProcess(message as T)
            }
        }
    }

    protected open fun startProcess(message: T) {
        message.setCode(processEndMsgCode)
        hold(getDuration(), message)
    }

    protected open fun endProcess(message: T) = assistantFinished(message)

}