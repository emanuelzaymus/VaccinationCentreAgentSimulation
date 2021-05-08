package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import OSPABA.*
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.messages.Message
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.WorkerState

abstract class VaccinationCentreActivityProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<Message>(id, mySim, myAgent) {

    override fun startProcess(message: Message) {
        message.worker!!.state = WorkerState.WORKING
        super.startProcess(message)
    }

    override fun endProcess(message: Message) {
        message.worker!!.state = WorkerState.FREE
        super.endProcess(message)
    }

}