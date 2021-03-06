package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination

import OSPABA.CommonAgent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.NURSES_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.LunchBreakScheduler

class NursesLunchBreakScheduler(mySim: Simulation, myAgent: CommonAgent) :
    LunchBreakScheduler(Ids.nursesLunchBreakScheduler, mySim, myAgent) {

    override val debugName = "NursesLunchBreakScheduler"
    override val scheduleAt = NURSES_LUNCH_BREAK_START

}