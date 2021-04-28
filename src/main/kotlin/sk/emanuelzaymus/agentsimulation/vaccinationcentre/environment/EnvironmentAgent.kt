package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreAgent

class EnvironmentAgent(mySim: Simulation, parent: Agent, numberOfPatients: Int) :
    VaccinationCentreAgent(Ids.environmentAgent, mySim, parent) {

    private val environmentManager = EnvironmentManager(mySim, this)
    private val patientArrivalsScheduler = PatientArrivalsScheduler(mySim, this, numberOfPatients)

    init {
        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.scheduleArrival)
        addOwnMessage(MessageCodes.patientLeaving)
    }

    override fun prepareReplication() {
        super.prepareReplication()

        patientArrivalsScheduler.restart()
    }

    override fun checkFinalState() {
        patientArrivalsScheduler.checkFinalState()
    }

}