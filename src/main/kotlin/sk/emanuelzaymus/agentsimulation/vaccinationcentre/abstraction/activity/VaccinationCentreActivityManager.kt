package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager

abstract class VaccinationCentreActivityManager(
    id: Int, mySim: Simulation, private val myAgent: VaccinationCentreActivityAgent<*>
) : VaccinationCentreManager(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val activityStartMsgCode: Int
    protected abstract val activityEndMsgCode: Int
    protected abstract val activityProcessId: Int
    protected abstract val lunchBreakSchedulerId: Int

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            MessageCodes.init -> scheduleLunchBreak(message)

            activityStartMsgCode -> tryStartActivity(message as Message)
            // Process - activity done
            IdList.finish -> when (message.sender().id()) {
                activityProcessId -> activityDone(message as Message)

                lunchBreakSchedulerId -> sendWorkersToLunchBreak()
            }
        }
    }

    private fun scheduleLunchBreak(message: MessageForm) {
        message.setAddressee(lunchBreakSchedulerId)

        startContinualAssistant(message)
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

    private fun sendWorkersToLunchBreak() {
//        TODO("implement")
    }

}