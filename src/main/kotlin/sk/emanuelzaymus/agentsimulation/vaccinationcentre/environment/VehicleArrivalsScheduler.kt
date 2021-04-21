package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.*
import OSPRNG.ExponentialRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class VehicleArrivalsScheduler(id: Int = Ids.vehicleArrivalsScheduler, mySim: Simulation, myAgent: CommonAgent) :
    Scheduler(id, mySim, myAgent) {

    companion object {
        private val arrivalsGenerator = ExponentialRNG(5.0)
    }

    override fun processMessage(message: MessageForm) {
        when (message.code()) {

            IdList.start -> {
                message.setCode(MessageCodes.newVehicle)

                hold(arrivalsGenerator.sample(), message)
            }

            MessageCodes.newVehicle -> {
                val copy: MessageForm = message.createCopy()

                hold(arrivalsGenerator.sample(), copy)
                assistantFinished(message)
            }
        }
    }

}