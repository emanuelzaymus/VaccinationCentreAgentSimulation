package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.Pool
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsPreparationMessage

class VaccinationManager(mySim: Simulation, private val myAgent: VaccinationAgent) :
    VaccinationCentreActivityManager(Ids.vaccinationManager, mySim, myAgent) {

    private val preparationMessagePool = Pool { InjectionsPreparationMessage(mySim) }

    override val debugName = "VaccinationManager"

    override val activityStartMsgCode = MessageCodes.vaccinationStart
    override val activityEndMsgCode = MessageCodes.vaccinationEnd
    override val activityProcessId = Ids.vaccinationProcess

    override fun processMessage(message: MessageForm) {
        super.processMessage(message)

        when (message.code()) {
            MessageCodes.injectionsPreparationEnd -> injectionsPreparationDone(message as InjectionsPreparationMessage)
        }
    }

    override fun activityDone(message: Message) {
        val nurse = message.worker as Nurse
        message.worker = null

        if (nurse.hasAnyInjectionLeft())
            startActivityIfAnyWaiting()
        else
            requestInjectionsPreparation(nurse)

        message.setCode(activityEndMsgCode)
        response(message)
    }

    private fun requestInjectionsPreparation(nurse: Nurse) {
        val message = preparationMessagePool.acquire().also { it.nurse = nurse }

        message.setCode(MessageCodes.injectionsPreparationStart)
        message.setAddressee(mySim().findAgent(Ids.injectionsAgent))

        request(message)
    }

    private fun injectionsPreparationDone(message: InjectionsPreparationMessage) {
        preparationMessagePool.release(message)

        startActivityIfAnyWaiting()
    }

}