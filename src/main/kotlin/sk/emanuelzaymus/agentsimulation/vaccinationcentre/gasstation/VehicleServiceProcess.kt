package sk.emanuelzaymus.agentsimulation.vaccinationcentre.gasstation

import OSPABA.*
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class VehicleServiceProcess(id: Int = Ids.vehicleServiceProcess, mySim: Simulation, myAgent: CommonAgent) :
    Process(id, mySim, myAgent) {

    companion object {
        private val serviceDuration = ExponentialRNG(4.0)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> {
                message.setCode(MessageCodes.serviceEnd)

                hold(serviceDuration.sample(), message)
            }
            
            MessageCodes.serviceEnd -> assistantFinished(message)
        }
    }

}