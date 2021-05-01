package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class WorkersBreakMessage(mySim: Simulation) : VaccinationCentreMessage(mySim) {

    var worker: VaccinationCentreWorker? = null

    override fun restart() {
        worker = null
    }

    override fun toString() = "${super.toString()} $worker (${deliveryTime()})"

}