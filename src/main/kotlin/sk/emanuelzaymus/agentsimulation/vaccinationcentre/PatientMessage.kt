package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject

class PatientMessage : MessageForm, IPooledObject {

    val patient: Patient

    constructor(sim: Simulation) : super(sim) {
        patient = Patient()
    }

    private constructor(original: PatientMessage) : super(original) {
        patient = original.patient.copy()
    }

    override fun createCopy(): MessageForm = PatientMessage(this)

    override fun restart() = patient.restart()

}