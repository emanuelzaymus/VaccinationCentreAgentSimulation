package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityManager

class RegistrationManager(mySim: Simulation, myAgent: RegistrationAgent) :
    VaccinationCentreActivityManager(Ids.registrationManager, mySim, myAgent) {

    override val debugName = "RegistrationManager"

    override val startActivityMsgCode = MessageCodes.patientRegistration
    override val activityDoneMsgCode = MessageCodes.patientRegistrationDone
    override val activityProcessId = Ids.registrationProcess

}