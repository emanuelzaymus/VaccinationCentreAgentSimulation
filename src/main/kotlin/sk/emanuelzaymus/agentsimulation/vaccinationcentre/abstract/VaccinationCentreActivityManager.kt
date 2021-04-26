package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message

abstract class VaccinationCentreActivityManager(
    id: Int, mySim: Simulation, private val myAgent: VaccinationCentreActivityAgent<*>
) : VaccinationCentreManager(id, mySim, myAgent) {

    protected abstract val debugName: String
    protected abstract val startActivityMsgCode: Int
    protected abstract val activityDoneMsgCode: Int
    protected abstract val activityProcessId: Int

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            startActivityMsgCode -> tryStartActivity(message as Message)

            IdList.finish -> activityDone(message as Message)
        }
    }

    private fun tryStartActivity(message: Message) {
        debug("$debugName - $startActivityMsgCode")

        message.patient.restartWaiting()
        message.patient.startWaiting()

        if (myAgent.workers.anyAvailable())
            startActivity(message)
        else
            myAgent.queue.enqueue(message)
    }

    private fun activityDone(message: Message) {
        debug("$debugName - finish")

        message.worker!!.isBusy = false
        message.worker = null

        if (myAgent.queue.size > 0)
            startActivity(myAgent.queue.dequeue())

        message.setCode(activityDoneMsgCode)
        response(message)
    }

    private fun startActivity(message: Message) {
        message.patient.stopWaiting()
        myAgent.waitingTimeStat.addSample(message.patient.getWaitingTotal())

        message.worker = myAgent.workers.getRandomAvailable().apply { isBusy = true }
        message.setAddressee(myAgent.findAssistant(activityProcessId))

        startContinualAssistant(message)
    }

}