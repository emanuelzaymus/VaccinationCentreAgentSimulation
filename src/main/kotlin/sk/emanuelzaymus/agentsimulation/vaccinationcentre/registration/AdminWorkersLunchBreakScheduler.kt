package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.CommonAgent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.ADMIN_WORKERS_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.LunchBreakScheduler

class AdminWorkersLunchBreakScheduler(mySim: Simulation, myAgent: CommonAgent) :
    LunchBreakScheduler(Ids.adminWorkersLunchBreakScheduler, mySim, myAgent) {

    override val debugName = "AdminWorkersLunchScheduler"
    override val scheduleAt = ADMIN_WORKERS_LUNCH_BREAK_START

}