package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.*
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess

class LunchProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<MessageForm>(Ids.lunchProcess, mySim, myAgent) {

    companion object {
        private val lunchDuration = TriangularRNG(LUNCH_DURATION_MIN, LUNCH_DURATION_MODE, LUNCH_DURATION_MAX)
    }

    override val debugName = "LunchProcess"
    override val processEndMsgCode = MessageCodes.lunchEnd

    override fun getDuration(): Double = lunchDuration.sample()

}