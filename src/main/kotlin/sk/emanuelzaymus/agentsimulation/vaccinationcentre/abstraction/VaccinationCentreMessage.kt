package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes

abstract class VaccinationCentreMessage(mySim: Simulation) : MessageForm(mySim), IPooledObject {

    override fun createCopy(): MessageForm =
        throw NotImplementedError("VaccinationCentreMessage::createCopy not implemented")

    override fun toString(): String = "%-26s -".format(MessageCodes.getName(code()))

}