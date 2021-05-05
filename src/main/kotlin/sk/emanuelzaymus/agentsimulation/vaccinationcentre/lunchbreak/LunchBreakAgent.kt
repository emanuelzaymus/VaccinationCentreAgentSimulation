package sk.emanuelzaymus.agentsimulation.vaccinationcentre.lunchbreak

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class LunchBreakAgent(mySim: Simulation, parent: Agent?) : VaccinationCentreAgent(Ids.lunchBreakAgent, mySim, parent) {

    init {
        LunchBreakManager(mySim, this)
        ToCanteenTransferProcess(mySim, this)
        LunchProcess(mySim, this)
        FromCanteenTransferProcess(mySim, this)

        addOwnMessage(MessageCodes.lunchBreakStart)
        addOwnMessage(MessageCodes.lunchBreakEnd)

        addOwnMessage(MessageCodes.transferEnd)
        addOwnMessage(MessageCodes.lunchEnd)
    }

}