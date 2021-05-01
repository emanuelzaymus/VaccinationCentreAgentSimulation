package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreMessage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class Message(mySim: Simulation) : VaccinationCentreMessage(mySim) {

    val patient = Patient(mySim)

    var worker: VaccinationCentreWorker? = null
        set(value) {
            if (field != null && value != null)
                throw IllegalStateException("Worker is not null - cannot be assigned with non-null value.")
            field = value
        }

    override fun restart() {
        if (worker != null)
            throw IllegalStateException("While restarting Message worker was not null.")
        worker = null

        patient.restart()
    }

    override fun toString() = "${super.toString()} Patient: $patient, Worker: $worker (${super.deliveryTime()})"

}