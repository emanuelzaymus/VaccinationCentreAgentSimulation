package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.Nurse

class InjectionsPreparationMessage(mySim: Simulation) : MessageForm(mySim), IPooledObject {

    var nurse: Nurse? = null

    override fun createCopy(): MessageForm =
        throw NotImplementedError("InjectionPreparationMessage::createCopy not implemented")

    override fun restart() {
        nurse = null
    }

    override fun toString(): String = "%-26s - $nurse ${super.toString()}".format(MessageCodes.getName(code()))

}