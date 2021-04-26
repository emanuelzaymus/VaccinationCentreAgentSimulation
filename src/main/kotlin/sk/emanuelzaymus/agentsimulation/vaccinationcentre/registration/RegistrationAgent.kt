package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Agent
import OSPABA.Simulation
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityAgent

class RegistrationAgent(mySim: Simulation, parent: Agent, numberOfAdminWorkers: Int) :
    VaccinationCentreActivityAgent<AdministrativeWorker>(
        Ids.registrationAgent, mySim, parent, numberOfAdminWorkers, { AdministrativeWorker(WStat(mySim)) }
    ) {

    override val statsName = "Registration"

    init {
        RegistrationManager(mySim, this)
        RegistrationProcess(mySim, this)

        addOwnMessage(MessageCodes.registration)
        addOwnMessage(MessageCodes.registrationDone)
    }

}