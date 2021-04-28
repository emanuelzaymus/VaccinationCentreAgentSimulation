package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.CommonAgent
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_DURATION_MODE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityProcess

class VaccinationProcess(mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreActivityProcess(Ids.vaccinationProcess, mySim, myAgent) {

    companion object {
        private val vaccinationDuration =
            TriangularRNG(VACCINATION_DURATION_MIN, VACCINATION_DURATION_MODE, VACCINATION_DURATION_MAX)
    }

    override val debugName: String = "VaccinationProcess"
    override val activityEndMsgCode: Int = MessageCodes.vaccinationEnd

    override fun getDuration(): Double = vaccinationDuration.sample()

}