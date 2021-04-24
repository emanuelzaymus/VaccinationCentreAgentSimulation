package sk.emanuelzaymus.agentsimulation.utils.busylist

import sk.emanuelzaymus.agentsimulation.utils.IReusable

interface IBusyObject : IReusable {

    var isBusy: Boolean

//    fun isBusy(): Boolean
//    fun setBusy(busy: Boolean, eventTime: Double, commonTotalTime: Double)
}