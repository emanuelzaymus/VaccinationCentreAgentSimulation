package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer

import OSPABA.CommonAgent
import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreProcess

/**
 * Transfer process.S
 */
abstract class VaccinationCentreTransferProcess<T : MessageForm>(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreProcess<T>(id, mySim, myAgent) {
    override val processEndMsgCode = MessageCodes.transferEnd
}