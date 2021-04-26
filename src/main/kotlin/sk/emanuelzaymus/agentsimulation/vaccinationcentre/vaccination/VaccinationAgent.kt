package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.Agent
import OSPABA.Simulation
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityAgent

class VaccinationAgent(mySim: Simulation, parent: Agent, numberOfNurses: Int) :
    VaccinationCentreActivityAgent<Nurse>(
        Ids.vaccinationAgent, mySim, parent, numberOfNurses, { Nurse(WStat(mySim)) }
    ) {

    override val statsName = "Vaccination"

    init {
        VaccinationManager(mySim, this)
        VaccinationProcess(mySim, this)

        addOwnMessage(MessageCodes.vaccination)
        addOwnMessage(MessageCodes.vaccinationDone)
    }

}