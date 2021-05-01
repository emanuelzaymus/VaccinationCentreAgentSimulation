package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.CommonAgent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.DOCTORS_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.LunchBreakScheduler

class DoctorsLunchBreakScheduler(mySim: Simulation, myAgent: CommonAgent) :
    LunchBreakScheduler(Ids.doctorsLunchBreakScheduler, mySim, myAgent) {

    override val debugName = "DoctorsLunchBreakScheduler"
    override val scheduleAt = DOCTORS_LUNCH_BREAK_START

}