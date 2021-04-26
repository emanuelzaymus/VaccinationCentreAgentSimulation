package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityManager

class VaccinationManager(mySim: Simulation, myAgent: VaccinationAgent) :
    VaccinationCentreActivityManager(Ids.vaccinationManager, mySim, myAgent) {

    override val debugName = "VaccinationManager"

    override val startActivityMsgCode = MessageCodes.vaccination
    override val activityDoneMsgCode = MessageCodes.vaccinationDone
    override val activityProcessId = Ids.vaccinationProcess

}