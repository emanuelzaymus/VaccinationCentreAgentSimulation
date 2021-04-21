package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class RegistrationManager(id: Int = Ids.registrationManager, mySim: Simulation, myAgent: Agent) :
    Manager(id, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.patientRegistration ->
                if (myAgent().isWorking) {
                    (message as Message).waitingStart = mySim().currentTime()

                    myAgent().patientQueue.enqueue(message)
                } else {
                    startWork(message)
                }

            IdList.finish -> {
                myAgent().isWorking = false
                myAgent().waitingTimeStat.addSample((message as Message).waitingTotal)

                if (myAgent().patientQueue.size > 0) {
                    val nextMessage: Message = myAgent().patientQueue.dequeue() as Message

                    nextMessage.waitingTotal = mySim().currentTime() - nextMessage.waitingStart
                    startWork(nextMessage)
                }

                message.setCode(MessageCodes.patientRegistrationDone)

                response(message)
            }
        }
    }

    private fun startWork(message: MessageForm) {
        myAgent().isWorking = true
        message.setAddressee(myAgent().findAssistant(Ids.registrationProcess))

        startContinualAssistant(message)
    }

    override fun myAgent(): RegistrationAgent = super.myAgent() as RegistrationAgent

}