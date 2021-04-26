package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.IdList
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.debug
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreManager

class ExaminationManager(mySim: Simulation, private val myAgent: ExaminationAgent) :
    VaccinationCentreManager(Ids.examinationManager, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.examination -> tryExaminePatient(message as Message)

            IdList.finish -> examinationDone(message as Message)
        }
    }

    private fun tryExaminePatient(message: Message) {
        debug("ExaminationManager - examination")

        message.patient.restartWaiting()
        message.patient.startWaiting()

        if (myAgent.workers.anyAvailable())
            startExamination(message)
        else
            myAgent.queue.enqueue(message)
    }

    private fun examinationDone(message: Message) {
        debug("ExaminationManager - finish")

        message.doctor!!.isBusy = false
        message.doctor = null

        if (myAgent.queue.size > 0)
            startExamination(myAgent.queue.dequeue())

        message.setCode(MessageCodes.examinationDone)
        response(message)
    }

    private fun startExamination(message: Message) {
        message.patient.stopWaiting()
        myAgent.waitingTimeStat.addSample(message.patient.getWaitingTotal())

        message.doctor = myAgent.workers.getRandomAvailable().apply { isBusy = true }
        message.setAddressee(myAgent.findAssistant(Ids.examinationProcess))

        startContinualAssistant(message)
    }

}