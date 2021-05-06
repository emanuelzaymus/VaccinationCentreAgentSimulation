package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.NURSES_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak.WorkersBreakMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsPreparationMessage

class VaccinationManager(mySim: Simulation, myAgent: VaccinationAgent) :
    VaccinationCentreActivityManager(Ids.vaccinationManager, mySim, myAgent) {

    private val preparationMessagePool = Pool { InjectionsPreparationMessage(mySim) }

    override val debugName = "VaccinationManager"

    override val activityStartMsgCode = MessageCodes.vaccinationStart
    override val activityEndMsgCode = MessageCodes.vaccinationEnd
    override val activityProcessId = Ids.vaccinationProcess
    override val lunchBreakSchedulerId = Ids.nursesLunchBreakScheduler
    override val lunchBreakStart = NURSES_LUNCH_BREAK_START

    override fun processMessage(message: MessageForm) {
        super.processMessage(message)

        when (message.code()) {
            MessageCodes.injectionsPreparationEnd -> injectionsPreparationDone(message as InjectionsPreparationMessage)
        }
    }

    override fun activityDone(message: Message) {
        val nurse = message.worker as Nurse
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
        if (!nurse.hasAnyInjectionLeft()) {
            requestInjectionsPreparation(nurse)
        }
        super.lunchBreakDone(message)
    }

    private fun requestInjectionsPreparation(nurse: Nurse) {
        val message = preparationMessagePool.acquire().also { it.nurse = nurse }

        message.setCode(MessageCodes.injectionsPreparationStart)
        message.setAddressee(Ids.injectionsAgent)

        request(message)
    }

    private fun injectionsPreparationDone(message: InjectionsPreparationMessage) {
        preparationMessagePool.release(message)

        startActivityIfPossible()
    }

}