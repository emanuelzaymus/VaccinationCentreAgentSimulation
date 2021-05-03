package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsPreparationMessage

class VaccinationManager(mySim: Simulation, myAgent: VaccinationAgent) :
    VaccinationCentreActivityManager(Ids.vaccinationManager, mySim, myAgent) {

    private val preparationMessagePool = Pool { InjectionsPreparationMessage(mySim) }

    override val debugName = "VaccinationManager"

    override val activityStartMsgCode = MessageCodes.vaccinationStart
    override val activityEndMsgCode = MessageCodes.vaccinationEnd
    override val activityProcessId = Ids.vaccinationProcess
    override val lunchBreakSchedulerId = Ids.nursesLunchBreakScheduler

    override fun processMessage(message: MessageForm) {
        super.processMessage(message)

        when (message.code()) {

            MessageCodes.injectionsPreparationEnd -> startFromInjectionsTransfer(message as InjectionsPreparationMessage)

            IdList.finish -> when (message.sender().id()) {

                Ids.toInjectionsTransferProcess -> requestInjectionsPreparation(message as InjectionsPreparationMessage)

                Ids.fromInjectionsTransferProcess -> fromInjectionsTransferDone(message as InjectionsPreparationMessage)
            }
        }
    }

    override fun activityDone(message: Message) {
        val nurse = message.worker as Nurse
        message.worker = null

        if (nurse.hasAnyInjectionLeft())
            startActivityIfAnyWaiting()
        else
            startToInjectionsTransfer(nurse)

        message.setCode(activityEndMsgCode)
        response(message)
    }

    private fun startToInjectionsTransfer(nurse: Nurse) {
        val message = preparationMessagePool.acquire().also { it.nurse = nurse }
        message.setAddressee(Ids.toInjectionsTransferProcess)

        startContinualAssistant(message)
    }

    private fun requestInjectionsPreparation(message: InjectionsPreparationMessage) {
        message.setCode(MessageCodes.injectionsPreparationStart)
        message.setAddressee(Ids.injectionsAgent)

        request(message)
    }

    private fun startFromInjectionsTransfer(message: InjectionsPreparationMessage) {
        message.setAddressee(Ids.fromInjectionsTransferProcess)

        startContinualAssistant(message)
    }

    private fun fromInjectionsTransferDone(message: InjectionsPreparationMessage) {
        preparationMessagePool.release(message)

        startActivityIfAnyWaiting()
    }

}