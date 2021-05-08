package sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message

class WaitingProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<Message>(Ids.waitingProcess, mySim, myAgent) {

    companion object {
        private val waitingDuration = UniformContinuousRNG(.0, 1.0)
    }

    override val debugName = "WaitingProcess"
    override val processEndMsgCode = MessageCodes.waitingEnd

    override fun getDuration(): Double =
        if (waitingDuration.sample() < C.WAITING_LESS_PROBABILITY) C.WAITING_DURATION_LESS
        else C.WAITING_DURATION_MORE

}