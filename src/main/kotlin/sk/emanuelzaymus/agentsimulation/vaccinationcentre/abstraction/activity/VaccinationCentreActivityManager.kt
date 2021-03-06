package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.lunchbreak.WorkersBreakMessage

/**
 * Manager for reasoning for VaccinationCentreActivityAgent.
 */
abstract class VaccinationCentreActivityManager(
    id: Int, private val mySim: Simulation, private val myAgent: VaccinationCentreActivityAgent<*>
) : VaccinationCentreManager(id, mySim, myAgent) {

    protected val breakMessagePool = Pool { WorkersBreakMessage(mySim) }

    protected abstract val debugName: String
    protected abstract val activityStartMsgCode: Int
    protected abstract val activityEndMsgCode: Int
    protected abstract val activityProcessId: Int
    protected abstract val lunchBreakSchedulerId: Int
    protected abstract val lunchBreakStart: Double

    override fun processMessage(message: MessageForm) {
        debug(debugName, message)

        when (message.code()) {
            MessageCodes.init -> scheduleLunchBreak(message)

            activityStartMsgCode -> tryStartActivity(message as Message)

            MessageCodes.lunchBreakEnd -> lunchBreakDone(message as WorkersBreakMessage)
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
        val worker = message.worker!!
        worker.isBusy = false
        message.worker = null

        if (!tryStartLunchBreak(worker)) {
            startActivityIfPossible()
        }

        message.setCode(activityEndMsgCode)
        response(message)
    }

    protected fun startActivityIfPossible() {
        if (myAgent.queue.isNotEmpty() && myAgent.workers.anyAvailable())
            startActivity(myAgent.queue.dequeue())
    }

    private fun startActivity(message: Message) {
        message.patient.stopWaiting()
        myAgent.waitingTimeStat.addSample(message.patient.getWaitingTotal())

        message.worker = myAgent.workers.getRandomAvailable().apply {
            isBusy = true
        }
        message.setAddressee(activityProcessId)

        startContinualAssistant(message)
    }

    protected open fun lunchBreakDone(message: WorkersBreakMessage) {
        message.worker!!.setLunchBreakDone()

        breakMessagePool.release(message)

        sendWorkersToLunchBreak()
        startActivityIfPossible()
    }

    protected fun sendWorkersToLunchBreak() =
        myAgent.workers.filter { !it.isBusy }.shuffled().forEach { tryStartLunchBreak(it) }

    protected fun tryStartLunchBreak(worker: VaccinationCentreWorker): Boolean {
        if (worker.isBusy) {
            throw IllegalArgumentException("Cannot try to start lunch break because the worker is busy.")
        }
        if (!worker.hadLunchBreak && mySim.currentTime() >= lunchBreakStart && lessThenHalfWorkersIsHavingLunchBreak()) {
            requestLunchBreak(worker)
            return true
        }
        return false
    }

    private fun lessThenHalfWorkersIsHavingLunchBreak(): Boolean =
        myAgent.workers.size / 2 > myAgent.workers.count { it.isHavingLunchBreak } || myAgent.workers.size == 1

    private fun requestLunchBreak(worker: VaccinationCentreWorker) {
        worker.setIsHavingLunchBreak()

        val message = breakMessagePool.acquire().also {
            it.worker = worker
            it.setCode(MessageCodes.lunchBreakStart)
            it.setAddressee(Ids.modelAgent)
        }
        request(message)
    }

}