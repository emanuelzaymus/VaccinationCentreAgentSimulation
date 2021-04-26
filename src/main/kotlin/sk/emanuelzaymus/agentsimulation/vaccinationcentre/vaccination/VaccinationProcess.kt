package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.Simulation
import OSPRNG.TriangularRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_DURATION_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_DURATION_MIN
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VACCINATION_DURATION_MODE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreProcess

class VaccinationProcess(mySim: Simulation, myAgent: VaccinationAgent) :
    VaccinationCentreProcess(Ids.vaccinationProcess, mySim, myAgent) {

    companion object {
        private val vaccinationDuration =
            TriangularRNG(VACCINATION_DURATION_MIN, VACCINATION_DURATION_MODE, VACCINATION_DURATION_MAX)
    }

    override val debugName: String = "VaccinationProcess"
    override val activityDoneMsgCode: Int = MessageCodes.vaccinationDone

    override fun getDuration(): Double = vaccinationDuration.sample()

}