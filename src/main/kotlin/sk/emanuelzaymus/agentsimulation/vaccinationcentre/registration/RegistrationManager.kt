package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.ADMIN_WORKERS_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager

class RegistrationManager(mySim: Simulation, myAgent: RegistrationAgent) :
    VaccinationCentreActivityManager(Ids.registrationManager, mySim, myAgent) {

    override val debugName = "RegistrationManager"

    override val activityStartMsgCode = MessageCodes.registrationStart
    override val activityEndMsgCode = MessageCodes.registrationEnd
    override val activityProcessId = Ids.registrationProcess
    override val lunchBreakSchedulerId = Ids.adminWorkersLunchBreakScheduler
    override val lunchBreakStart = ADMIN_WORKERS_LUNCH_BREAK_START

}