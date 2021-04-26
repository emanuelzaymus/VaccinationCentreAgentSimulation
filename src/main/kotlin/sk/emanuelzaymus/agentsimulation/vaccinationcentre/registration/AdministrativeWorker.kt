package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject

class AdministrativeWorker(val workloadStat: WStat) : IBusyObject {

    override var isBusy: Boolean = false
        set(value) {
            if (value == field)
                throw IllegalArgumentException("Cannot reassigned isBusy with the same value.")
            field = value
            workloadStat.addSample(if (value) 1.0 else .0)
        }

    override fun checkFinalState() {
        if (isBusy) throw IllegalStateException("This AdministrativeWorker is still working.")
    }

    override fun restart() = workloadStat.clear()

}