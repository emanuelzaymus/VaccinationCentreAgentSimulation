package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.MessageForm

const val DEBUG_MODE = false
const val PRINT_REPL_STATS = false

fun debug(name: String, message: Message) {
    if (DEBUG_MODE)
        debug(name, message.toString())
}

fun debug(name: String, messageForm: MessageForm) {
    if (DEBUG_MODE) {
        if (messageForm is Message)
            debug(name, messageForm)
        else if (messageForm is InitMessage)
            debug(name, messageForm)
    }
}

private fun debug(name: String, initMessage: InitMessage) {
    if (DEBUG_MODE)
        debug(name, initMessage.toString())
}

private fun debug(name: String, message: String) {
    if (DEBUG_MODE)
        println("$name - $message")
}