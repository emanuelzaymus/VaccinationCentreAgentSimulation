package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.transfer

import OSPABA.CommonAgent
import OSPABA.MessageForm
import OSPABA.Simulation

abstract class VaccinationCentreWorkerTransferProcess(id: Int, mySim: Simulation, myAgent: CommonAgent) :
    VaccinationCentreTransferProcess(id, mySim, myAgent) {

    override fun startTransfer(message: MessageForm) {
        (message as WorkerTransferMessage).worker!!.inTransfer = true
        super.startTransfer(message)
    }

    override fun endTransfer(message: MessageForm) {
        (message as WorkerTransferMessage).worker!!.inTransfer = false
        super.endTransfer(message)
    }

}