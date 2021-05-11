package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination.injections

import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.OPENING_PACKAGE_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.OPENING_PACKAGE_DURATION_MIN

class OpeningNewPackageProcess(mySim: Simulation, private val myAgent: InjectionsAgent) :
    VaccinationCentreProcess<InjectionsPreparationMessage>(Ids.openingNewPackageProcess, mySim, myAgent) {

    companion object {
        private val openPackageDuration
            get() = if (useZeroDuration) zeroDuration
            else UniformContinuousRNG(OPENING_PACKAGE_DURATION_MIN, OPENING_PACKAGE_DURATION_MAX)
    }

    override val debugName = "OpenNewPackageProcess"
    override val processEndMsgCode = MessageCodes.openingNewPackageEnd

    override fun getDuration(): Double = openPackageDuration.sample()

    override fun startProcess(message: InjectionsPreparationMessage) {
        message.nurse!!.state = WorkerState.OPENING_NEW_PACKAGE
        super.startProcess(message)
    }

    override fun endProcess(message: InjectionsPreparationMessage) {
        myAgent.restartVaccinesInPackageLeft()
        super.endProcess(message)
    }

}
