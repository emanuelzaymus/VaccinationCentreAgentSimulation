package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.*
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.*

class InjectionsPreparationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<InjectionsPreparationMessage>(Ids.injectionsPreparationProcess, mySim, myAgent) {

    companion object {
        private val preparationDuration
            get() = if (useZeroDuration) zeroDuration
            else TriangularRNG(INJECTION_PREP_DURATION_MIN, INJECTION_PREP_DURATION_MODE, INJECTION_PREP_DURATION_MAX)
    }

    override val debugName = "InjectionsPreparationProcess"
    override val processEndMsgCode = MessageCodes.injectionsPreparationEnd

    override fun getDuration(): Double = List(INJECTIONS_COUNT_TO_PREPARE) { preparationDuration.sample() }.sum()

    override fun startProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.state = WorkerState.PREPARING_INJECTIONS
        super.startProcess(message)
    }

    override fun endProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.restartInjectionsLeft()
        super.endProcess(message)
    }

}
