package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination.Doctor
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration.AdministrativeWorker

class Message : MessageForm, IPooledObject {

    val patient: Patient
    var administrativeWorker: AdministrativeWorker? = null
    var doctor: Doctor? = null

    constructor(mySim: Simulation) : super(mySim) {
        patient = Patient(mySim)
    }

    private constructor(original: Message) : super(original) {
        patient = original.patient.copy()
    }

    override fun createCopy(): MessageForm = Message(this)

    override fun restart() {
        if (administrativeWorker != null)
            throw IllegalStateException("While restarting Message administrativeWorker was not null.")
        administrativeWorker = null

        if (doctor != null)
            throw IllegalStateException("While restarting Message doctor was not null.")
        doctor = null

        patient.restart()
    }

}