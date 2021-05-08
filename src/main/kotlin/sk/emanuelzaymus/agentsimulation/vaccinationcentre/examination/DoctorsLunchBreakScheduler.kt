package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.CommonAgent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.LunchBreakScheduler
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class DoctorsLunchBreakScheduler(mySim: Simulation, myAgent: CommonAgent) :
    LunchBreakScheduler(Ids.doctorsLunchBreakScheduler, mySim, myAgent) {

    override val debugName = "DoctorsLunchBreakScheduler"
    override val scheduleAt = C.DOCTORS_LUNCH_BREAK_START

}