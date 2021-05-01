package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer

import OSPABA.MessageForm
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.utils.pool.IPooledObject
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

class WorkerTransferMessage(mySim: Simulation) : MessageForm(mySim), IPooledObject {

    var worker: VaccinationCentreWorker? = null

    override fun createCopy(): MessageForm {
        throw NotImplementedError("WorkerTransferMessage::createCopy not implemented")
    }

    override fun restart() {
        worker = null
    }

    // TODO: SPRAV JEDNEHO PREDKA S toString a createCopy, restart...
    override fun toString(): String = "%-26s - $worker ${super.toString()}".format(MessageCodes.getName(code()))

}