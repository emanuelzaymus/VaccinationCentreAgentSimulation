package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPABA.Agent
import OSPABA.Simulation
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.ADMIN_WORKERS_LUNCH_BREAK_START
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityAgent

class RegistrationAgent(mySim: Simulation, parent: Agent, numberOfAdminWorkers: Int) :
    VaccinationCentreActivityAgent<AdministrativeWorker>(
        Ids.registrationAgent, mySim, parent, numberOfAdminWorkers, { AdministrativeWorker(it + 1, WStat(mySim)) }
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
    }

}