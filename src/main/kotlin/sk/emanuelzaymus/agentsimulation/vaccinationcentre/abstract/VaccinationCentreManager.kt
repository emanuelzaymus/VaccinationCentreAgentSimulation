package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.IReusable

abstract class VaccinationCentreManager(id: Int, mySim: Simulation, myAgent: Agent) : Manager(id, mySim, myAgent),
    IReusable {

    override fun restart() {}

    override fun checkFinalState() {}

}