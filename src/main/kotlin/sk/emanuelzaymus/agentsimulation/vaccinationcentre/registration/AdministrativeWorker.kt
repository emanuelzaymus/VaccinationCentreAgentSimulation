package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import sk.emanuelzaymus.agentsimulation.utils.busylist.IBusyObject

class AdministrativeWorker : IBusyObject {

    override var isBusy: Boolean = false
        set(value) {
            if (value == field)
                throw IllegalArgumentException("Cannot reassigned isBusy with the same value.")
            field = value
        }

//    override fun isBusy(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun setBusy(busy: Boolean, eventTime: Double, commonTotalTime: Double) {
//        TODO("Not yet implemented")
//    }

    override fun checkFinalState() {
        if (isBusy) throw IllegalStateException("This AdministrativeWorker is still working.")
    }

    override fun restart() {
//        if (isBusy) {
//            isBusy = false
//        }
    }

}