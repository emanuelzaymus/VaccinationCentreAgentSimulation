package sk.emanuelzaymus.agentsimulation.vaccinationcentre

var DEBUG_MODE = true
val PRINT_REPL_STATS get() = DEBUG_MODE
val PRINT_SIM_STATS get() = DEBUG_MODE

fun debug(name: String, messageForm: Any) {
    if (DEBUG_MODE)
        println("%-28s - $messageForm".format(name))
}