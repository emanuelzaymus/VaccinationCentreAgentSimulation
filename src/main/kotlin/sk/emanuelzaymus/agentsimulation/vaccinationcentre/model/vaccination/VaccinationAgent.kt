package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityAgent

class VaccinationAgent(mySim: Simulation, parent: Agent, numberOfNurses: Int) :
    VaccinationCentreActivityAgent<Nurse>(
        Ids.vaccinationAgent, mySim, parent, numberOfNurses, { Nurse(it + 1, mySim) }
    ) {

    override val statsName = "Vaccination"

    init {
        VaccinationManager(mySim, this)
        VaccinationProcess(mySim, this)
        NursesLunchBreakScheduler(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.vaccinationStart)
        addOwnMessage(MessageCodes.vaccinationEnd)
        addOwnMessage(MessageCodes.lunchBreakNow)
        addOwnMessage(MessageCodes.lunchBreakEnd)
        addOwnMessage(MessageCodes.injectionsPreparationStart)
        addOwnMessage(MessageCodes.injectionsPreparationEnd)
    }

}