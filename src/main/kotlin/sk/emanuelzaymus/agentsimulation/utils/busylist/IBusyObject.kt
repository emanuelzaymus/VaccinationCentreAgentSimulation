package sk.emanuelzaymus.agentsimulation.utils.busylist

import sk.emanuelzaymus.agentsimulation.utils.IReusable

interface IBusyObject : IReusable {
    val isBusy: Boolean
}