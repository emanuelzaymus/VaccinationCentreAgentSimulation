package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.CommonAgent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.LunchBreakScheduler
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class NursesLunchBreakScheduler(mySim: Simulation, myAgent: CommonAgent) :
    LunchBreakScheduler(Ids.nursesLunchBreakScheduler, mySim, myAgent) {

    override val debugName = "NursesLunchBreakScheduler"
    override val scheduleAt = C.NURSES_LUNCH_BREAK_START

}