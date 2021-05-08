package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.waiting

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message

class WaitingProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<Message>(Ids.waitingProcess, mySim, myAgent) {

    companion object {
        private val waitingDuration = UniformContinuousRNG(.0, 1.0)
    }

    override val debugName = "WaitingProcess"
    override val processEndMsgCode = MessageCodes.waitingEnd

    override fun getDuration(): Double =
        if (waitingDuration.sample() < WAITING_LESS_PROBABILITY) WAITING_DURATION_LESS
        else WAITING_DURATION_MORE

}