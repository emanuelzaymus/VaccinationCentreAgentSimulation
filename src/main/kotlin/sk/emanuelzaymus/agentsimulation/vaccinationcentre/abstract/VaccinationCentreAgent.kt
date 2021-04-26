package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.IReusable

open class VaccinationCentreAgent(id: Int, mySim: Simulation, parent: Agent?) : Agent(id, mySim, parent), IReusable {

    override fun restart() {}

    override fun checkFinalState() {}

}