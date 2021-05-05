package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.IReusable

abstract class VaccinationCentreAgent(id: Int, mySim: Simulation, parent: Agent?) : Agent(id, mySim, parent),
    IReusable, IWStatsEntity {

    override fun restart() {}

    override fun checkFinalState() {}

    override fun countLastStats() {}

}