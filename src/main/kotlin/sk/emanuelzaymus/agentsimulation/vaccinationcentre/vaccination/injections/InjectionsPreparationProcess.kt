package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class InjectionsPreparationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<InjectionsPreparationMessage>(Ids.injectionsPreparationProcess, mySim, myAgent) {

    companion object {
        private val preparationDuration =
            TriangularRNG(C.INJECTION_PREP_DURATION_MIN, C.INJECTION_PREP_DURATION_MODE, C.INJECTION_PREP_DURATION_MAX)
    }

    override val debugName = "InjectionsPreparationProcess"
    override val processEndMsgCode = MessageCodes.injectionsPreparationEnd

    override fun getDuration(): Double = List(C.INJECTIONS_COUNT_TO_PREPARE) { preparationDuration.sample() }.sum()

    override fun startProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.state = WorkerState.PREPARING_INJECTIONS
        super.startProcess(message)
    }

    override fun endProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.restartInjectionsLeft()
        super.endProcess(message)
    }

}
