package sk.emanuelzaymus.agentsimulation.utils.busylist

import OSPRNG.UniformDiscreteRNG
import sk.emanuelzaymus.agentsimulation.utils.IReusable

class BusyList<T : IBusyObject>(numberOfBusyObjects: Int, init: (Int) -> T) : Iterable<T>, IReusable {

    private val randoms = List(numberOfBusyObjects - 1) { UniformDiscreteRNG(0, it + 1) }
    private val busyObjects = List(numberOfBusyObjects, init)

    val size: Int get() = busyObjects.size

    fun anyAvailable(): Boolean = busyObjects.any { !it.isBusy }

    fun getRandomAvailable(): T {
        val freeObjects = busyObjects.filter { !it.isBusy }

        if (freeObjects.isEmpty())
            throw IllegalStateException("None of the objects is free.")

        if (freeObjects.size == 1)
            return freeObjects[0]

        val sizeRandom = getRandomForSize(freeObjects.size)
        return freeObjects[sizeRandom.sample()]
    }

    override fun restart() = busyObjects.forEach { it.restart() }

    override fun checkFinalState() = busyObjects.forEach { it.checkFinalState() }

    private fun getRandomForSize(size: Int) = randoms[size - 2]

    override fun iterator(): Iterator<T> = busyObjects.iterator()

}