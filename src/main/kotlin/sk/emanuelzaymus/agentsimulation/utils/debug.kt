package sk.emanuelzaymus.agentsimulation.utils

private const val DEBUG_MODE = false

fun debug(info: String) {
    if (DEBUG_MODE)
        println(info)
}