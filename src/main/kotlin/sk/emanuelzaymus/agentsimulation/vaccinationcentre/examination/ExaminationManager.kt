package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityManager

class ExaminationManager(mySim: Simulation, myAgent: ExaminationAgent) :
    VaccinationCentreActivityManager(Ids.examinationManager, mySim, myAgent) {

    override val debugName = "ExaminationManager"

    override val startActivityMsgCode = MessageCodes.examination
    override val activityDoneMsgCode = MessageCodes.examinationDone
    override val activityProcessId = Ids.examinationProcess

}