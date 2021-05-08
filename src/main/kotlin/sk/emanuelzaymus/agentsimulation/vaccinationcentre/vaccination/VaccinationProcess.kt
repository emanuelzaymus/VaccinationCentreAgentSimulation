package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message

class VaccinationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.vaccinationProcess, mySim, myAgent) {

    companion object {
        private val vaccinationDuration =
            TriangularRNG(VACCINATION_DURATION_MIN, VACCINATION_DURATION_MODE, VACCINATION_DURATION_MAX)
    }

    override val debugName: String = "VaccinationProcess"
    override val processEndMsgCode: Int = MessageCodes.vaccinationEnd

    override fun getDuration(): Double = vaccinationDuration.sample()

    override fun startProcess(message: Message) {
        (message.worker as Nurse).decrementInjectionsLeft()
        super.startProcess(message)
    }

}