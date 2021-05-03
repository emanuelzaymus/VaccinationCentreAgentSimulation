package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState

abstract class VaccinationCentreActivityProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess(id, mySim, myAgent) {

    override fun startProcess(message: MessageForm) {
        startActivity(message as Message)
        message.worker!!.state = WorkerState.WORKING
        super.startProcess(message)
    }

    open fun startActivity(message: Message) {}

    override fun endProcess(message: MessageForm) {
        endActivity(message as Message)
        message.worker!!.state = WorkerState.FREE
        super.endProcess(message)
    }

    open fun endActivity(message: Message) {}

}