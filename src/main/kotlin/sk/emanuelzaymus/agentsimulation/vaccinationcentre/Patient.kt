package sk.emanuelzaymus.agentsimulation.vaccinationcentre

data class Patient(
    var waitingStart: Double = 0.0,
    var waitingTotal: Double = 0.0
)