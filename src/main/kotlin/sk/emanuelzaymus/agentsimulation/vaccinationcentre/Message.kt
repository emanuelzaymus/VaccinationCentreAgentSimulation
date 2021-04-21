package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm
import OSPABA.Simulation

class Message : MessageForm {

    var waitingStart: Double = 0.0
    var waitingTotal: Double = 0.0

    constructor(sim: Simulation) : super(sim)

    constructor(original: Message) : super(original) {
        waitingStart = original.waitingStart
        waitingTotal = original.waitingTotal
    }

    override fun createCopy(): MessageForm = Message(this)

}