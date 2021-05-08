package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityAgent

class ExaminationAgent(mySim: Simulation, parent: Agent, numberOfDoctors: Int) :
    VaccinationCentreActivityAgent<Doctor>(
        Ids.examinationAgent, mySim, parent, numberOfDoctors, { Doctor(it + 1, mySim) }
    ) {

    override val statsName = "Examination"

    init {
        ExaminationManager(mySim, this)
        ExaminationProcess(mySim, this)
        DoctorsLunchBreakScheduler(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.examinationStart)
        addOwnMessage(MessageCodes.examinationEnd)
        addOwnMessage(MessageCodes.lunchBreakNow)
        addOwnMessage(MessageCodes.lunchBreakEnd)
    }

}