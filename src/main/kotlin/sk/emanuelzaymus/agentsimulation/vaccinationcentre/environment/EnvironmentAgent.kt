package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.IReusable
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class EnvironmentAgent(mySim: Simulation, parent: Agent, numberOfPatients: Int) :
    Agent(Ids.environmentAgent, mySim, parent), IReusable {

    private val environmentManager = EnvironmentManager(mySim, this)
    private val patientArrivalsScheduler = PatientArrivalsScheduler(mySim, this, numberOfPatients)

    init {
        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.getNewPatient)
        addOwnMessage(MessageCodes.patientLeaving)
    }

    override fun prepareReplication() {
        super.prepareReplication()

        patientArrivalsScheduler.restart()
    }

    override fun checkFinalState() {
        patientArrivalsScheduler.checkFinalState()
    }

    override fun restart() {}

}