package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation

class Message : MessageForm {

    val patient: Patient

    constructor(sim: Simulation) : super(sim) {
        patient = Patient()
    }

    constructor(original: Message) : super(original) {
        patient = original.patient.copy()
    }

    override fun createCopy(): MessageForm = Message(this)

}