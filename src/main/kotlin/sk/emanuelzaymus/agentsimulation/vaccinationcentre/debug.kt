package sk.emanuelzaymus.agentsimulation.vaccinationcentre

const val DEBUG_MODE = true
const val PRINT_REPL_STATS = false

fun debug(name: String, messageForm: Any) {
    if (DEBUG_MODE)
        println("%-28s - $messageForm".format(name))
}