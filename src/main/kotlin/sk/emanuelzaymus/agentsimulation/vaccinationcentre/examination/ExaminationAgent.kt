package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPABA.Agent
import OSPABA.Simulation
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreActivityAgent

class ExaminationAgent(mySim: Simulation, parent: Agent, numberOfDoctors: Int) :
    VaccinationCentreActivityAgent<Doctor>(
        Ids.examinationAgent, mySim, parent, numberOfDoctors, { Doctor(WStat(mySim)) }
    ) {

    override val statsName = "Examination"

    init {
        ExaminationManager(mySim, this)
        ExaminationProcess(mySim, this)

        addOwnMessage(MessageCodes.examination)
        addOwnMessage(MessageCodes.examinationDone)
    }

}