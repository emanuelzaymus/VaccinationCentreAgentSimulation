package sk.emanuelzaymus.agentsimulation.vaccinationcentre.workersbreak

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class BreakAgent(mySim: Simulation, parent: Agent?) : VaccinationCentreAgent(Ids.breakAgent, mySim, parent) {

    init {
        BreakManager(mySim, this)
        ToCanteenTransferProcess(mySim, this)
        LunchProcess(mySim, this)
        FromCanteenTransferProcess(mySim, this)

        addOwnMessage(MessageCodes.breakStart)
        addOwnMessage(MessageCodes.breakEnd)
    }

}