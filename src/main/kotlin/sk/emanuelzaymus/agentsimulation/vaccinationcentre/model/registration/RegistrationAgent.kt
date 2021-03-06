package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.registration

import OSPABA.Agent
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityAgent

/**
 * Registers patient for vaccination
 */
class RegistrationAgent(mySim: Simulation, parent: Agent, numberOfAdminWorkers: Int) :
    VaccinationCentreActivityAgent<AdministrativeWorker>(
        Ids.registrationAgent, mySim, parent, numberOfAdminWorkers, { AdministrativeWorker(it + 1, mySim) }
    ) {

    override val statsName = "Registration"

    init {
        RegistrationManager(mySim, this)
        RegistrationProcess(mySim, this)
        AdminWorkersLunchBreakScheduler(mySim, this)

        addOwnMessage(MessageCodes.init)
        addOwnMessage(MessageCodes.registrationStart)
        addOwnMessage(MessageCodes.registrationEnd)
        addOwnMessage(MessageCodes.lunchBreakNow)
        addOwnMessage(MessageCodes.lunchBreakEnd)
    }

}