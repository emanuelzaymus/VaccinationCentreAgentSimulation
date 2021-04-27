package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.Doctor
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.AdministrativeWorker

class Message : MessageForm, IPooledObject {

    val patient: Patient

    var worker: VaccinationCentreWorker? = null
        set(value) {
            if (field != null && value != null)
                throw IllegalStateException("Worker is not null - cannot be assigned with non-null value.")
            field = value
        }

    constructor(mySim: Simulation) : super(mySim) {
        patient = Patient(mySim)
    }

    private constructor(original: Message) : super(original) {
        patient = original.patient.copy()
    }

    override fun createCopy(): MessageForm = Message(this)

    override fun restart() {
        if (worker != null)
            throw IllegalStateException("While restarting Message worker was not null.")
        worker = null

        patient.restart()
    }

    override fun toString(): String = "$patient, $worker, ${super.toString()}"

}