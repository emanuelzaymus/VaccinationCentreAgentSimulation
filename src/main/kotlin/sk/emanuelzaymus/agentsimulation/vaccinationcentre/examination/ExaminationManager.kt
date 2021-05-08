package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

class ExaminationManager(mySim: Simulation, myAgent: ExaminationAgent) :
    VaccinationCentreActivityManager(Ids.examinationManager, mySim, myAgent) {

    override val debugName = "ExaminationManager"

    override val activityStartMsgCode = MessageCodes.examinationStart
    override val activityEndMsgCode = MessageCodes.examinationEnd
    override val activityProcessId = Ids.examinationProcess
    override val lunchBreakSchedulerId = Ids.doctorsLunchBreakScheduler
    override val lunchBreakStart = C.DOCTORS_LUNCH_BREAK_START

}