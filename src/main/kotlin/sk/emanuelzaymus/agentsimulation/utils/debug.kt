package sk.emanuelzaymus.agentsimulation.utils

const val DEBUG_MODE = true

fun debug(info: String) {
    if (DEBUG_MODE)
        println(info)
}