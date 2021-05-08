package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class LunchProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<WorkersBreakMessage>(Ids.lunchProcess, mySim, myAgent) {

    companion object {
        private val lunchDuration = TriangularRNG(C.LUNCH_DURATION_MIN, C.LUNCH_DURATION_MODE, C.LUNCH_DURATION_MAX)
    }

    override val debugName = "LunchProcess"
    override val processEndMsgCode = MessageCodes.lunchEnd

    override fun getDuration(): Double = lunchDuration.sample()

    override fun startProcess(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.DINING
        super.startProcess(message)
    }

}