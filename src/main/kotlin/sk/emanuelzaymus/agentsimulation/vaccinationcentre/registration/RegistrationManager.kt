package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityManager

class RegistrationManager(mySim: Simulation, myAgent: RegistrationAgent) :
    VaccinationCentreActivityManager(Ids.registrationManager, mySim, myAgent) {

    override val debugName = "RegistrationManager"

    override val activityStartMsgCode = MessageCodes.registrationStart
    override val activityEndMsgCode = MessageCodes.registrationEnd
    override val activityProcessId = Ids.registrationProcess

}