package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager

abstract class VaccinationCentreActivityManager(
    id: Int, mySim: Simulation, private val myAgent: VaccinationCentreActivityAgent<*>
) : VaccinationCentreManager(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val activityStartMsgCode: Int
    protected abstract val activityEndMsgCode: Int
    protected abstract val activityProcessId: Int

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when {
            message.code() == activityStartMsgCode -> tryStartActivity(message as Message)
            // Process - activity done
            message.code() == IdList.finish && message.sender().id() == activityProcessId ->
                activityDone(message as Message)
        }
    }

    private fun tryStartActivity(message: Message) {
        message.patient.restartWaiting()
        message.patient.startWaiting()

        if (myAgent.workers.anyAvailable())
            startActivity(message)
        else
            myAgent.queue.enqueue(message)
    }

    protected open fun activityDone(message: Message) {
        message.worker = null

        startActivityIfAnyWaiting()

        message.setCode(activityEndMsgCode)
        response(message)
    }

    protected fun startActivityIfAnyWaiting() {
        if (myAgent.queue.isNotEmpty())
            startActivity(myAgent.queue.dequeue())
    }

    private fun startActivity(message: Message) {
        message.patient.stopWaiting()
        myAgent.waitingTimeStat.addSample(message.patient.getWaitingTotal())

        message.worker = myAgent.workers.getRandomAvailable()
        message.setAddressee(activityProcessId)

        startContinualAssistant(message)
    }

}