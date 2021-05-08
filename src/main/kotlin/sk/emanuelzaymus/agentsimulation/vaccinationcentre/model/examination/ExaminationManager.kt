package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.examination

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.DOCTORS_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager

class ExaminationManager(mySim: Simulation, myAgent: ExaminationAgent) :
    VaccinationCentreActivityManager(Ids.examinationManager, mySim, myAgent) {

    override val debugName = "ExaminationManager"

    override val activityStartMsgCode = MessageCodes.examinationStart
    override val activityEndMsgCode = MessageCodes.examinationEnd
    override val activityProcessId = Ids.examinationProcess
    override val lunchBreakSchedulerId = Ids.doctorsLunchBreakScheduler
    override val lunchBreakStart = DOCTORS_LUNCH_BREAK_START

}