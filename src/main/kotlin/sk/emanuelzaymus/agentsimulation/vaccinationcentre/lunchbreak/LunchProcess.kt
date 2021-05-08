package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.LUNCH_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.LUNCH_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.LUNCH_DURATION_MODE

class LunchProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<WorkersBreakMessage>(Ids.lunchProcess, mySim, myAgent) {

    companion object {
        private val lunchDuration
            get() = if (useZeroDuration) zeroDuration
            else TriangularRNG(LUNCH_DURATION_MIN, LUNCH_DURATION_MODE, LUNCH_DURATION_MAX)
    }

    override val debugName = "LunchProcess"
    override val processEndMsgCode = MessageCodes.lunchEnd

    override fun getDuration(): Double = lunchDuration.sample()

    override fun startProcess(message: WorkersBreakMessage) {
        message.worker!!.state = WorkerState.DINING
        super.startProcess(message)
    }

}