package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.IReusable

abstract class VaccinationCentreManager(id: Int, mySim: Simulation, myAgent: Agent) : Manager(id, mySim, myAgent),
    IReusable {

    // TODO: is it used?
    override fun restart() {}

    // TODO: is it used?
    override fun checkFinalState() {}

}