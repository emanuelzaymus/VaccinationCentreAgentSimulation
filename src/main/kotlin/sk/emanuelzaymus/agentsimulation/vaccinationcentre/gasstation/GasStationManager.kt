package sk.emanuelzaymus.agentsimulation.vaccinationcentre.gasstation

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class GasStationManager(id: Int = Ids.gasStationManager, mySim: Simulation, myAgent: Agent) :
    Manager(id, mySim, myAgent) {

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            MessageCodes.vehicleService ->
                if (myAgent().isWorking) {
                    (message as Message).waitingStart = mySim().currentTime()

                    myAgent().vehicleQueue.enqueue(message)
                } else {
                    startWork(message)
                }

            IdList.finish -> {
                myAgent().isWorking = false
                myAgent().waitingTimeStat.addSample((message as Message).waitingTotal)

                if (myAgent().vehicleQueue.size > 0) {
                    val nextMessage: Message = myAgent().vehicleQueue.dequeue() as Message

                    nextMessage.waitingTotal = mySim().currentTime() - nextMessage.waitingStart
                    startWork(nextMessage)
                }

                message.setCode(MessageCodes.vehicleServiceDone)

                response(message)
            }
        }
    }

    private fun startWork(message: MessageForm) {
        myAgent().isWorking = true
        message.setAddressee(myAgent().findAssistant(Ids.vehicleServiceProcess))

        startContinualAssistant(message)
    }

    override fun myAgent(): GasStationAgent = super.myAgent() as GasStationAgent

}