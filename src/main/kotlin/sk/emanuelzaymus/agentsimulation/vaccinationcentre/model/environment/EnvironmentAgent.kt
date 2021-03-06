package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

/**
 * Surroundings of the simulation, schedules patient arrivals.
 */
class EnvironmentAgent(mySim: Simulation, parent: Agent, numberOfPatients: Int, earlyArrivals: Boolean) :
    VaccinationCentreAgent(Ids.environmentAgent, mySim, parent) {

    private val environmentManager = EnvironmentManager(mySim, this)
    private val patientArrivalsScheduler = PatientArrivalsScheduler(mySim, this, numberOfPatients, earlyArrivals)

    init {
        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.scheduleArrival)
        addOwnMessage(MessageCodes.patientLeaving)
    }

    override fun prepareReplication() {
        super.prepareReplication()

        patientArrivalsScheduler.restart()
    }

    override fun checkFinalState() = patientArrivalsScheduler.checkFinalState()

}