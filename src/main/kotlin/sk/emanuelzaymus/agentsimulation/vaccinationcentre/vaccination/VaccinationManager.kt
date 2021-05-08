package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak.WorkersBreakMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsPreparationMessage

class VaccinationManager(mySim: Simulation, myAgent: VaccinationAgent) :
    VaccinationCentreActivityManager(Ids.vaccinationManager, mySim, myAgent) {

    private val preparationMessagePool = Pool { InjectionsPreparationMessage(mySim) }

    override val debugName = "VaccinationManager"

    override val activityStartMsgCode = MessageCodes.vaccinationStart
    override val activityEndMsgCode = MessageCodes.vaccinationEnd
    override val activityProcessId = Ids.vaccinationProcess
    override val lunchBreakSchedulerId = Ids.nursesLunchBreakScheduler
    override val lunchBreakStart = C.NURSES_LUNCH_BREAK_START

    override fun processMessage(message: MessageForm) {
        super.processMessage(message)

        when (message.code()) {
            MessageCodes.injectionsPreparationEnd -> injectionsPreparationDone(message as InjectionsPreparationMessage)
        }
    }

    override fun activityDone(message: Message) {
        val nurse = message.worker as Nurse
        nurse.isBusy = false
        message.worker = null

        if (!tryStartLunchBreak(nurse)) {
            if (nurse.hasAnyInjectionLeft())
                startActivityIfPossible()
            else
                requestInjectionsPreparation(nurse)
        }

        message.setCode(activityEndMsgCode)
        response(message)
    }

    override fun lunchBreakDone(message: WorkersBreakMessage) {
        val nurse = message.worker as Nurse
        nurse.setLunchBreakDone()

        breakMessagePool.release(message)
        sendWorkersToLunchBreak()

        if (!nurse.hasAnyInjectionLeft())
            requestInjectionsPreparation(nurse)
        else
            startActivityIfPossible()
    }

    private fun requestInjectionsPreparation(nurse: Nurse) {
        nurse.isBusy = true
        val message = preparationMessagePool.acquire().also { it.nurse = nurse }

        message.setCode(MessageCodes.injectionsPreparationStart)
        message.setAddressee(Ids.injectionsAgent)

        request(message)
    }

    private fun injectionsPreparationDone(message: InjectionsPreparationMessage) {
        message.nurse!!.isBusy = false
        preparationMessagePool.release(message)

        startActivityIfPossible()
    }

}