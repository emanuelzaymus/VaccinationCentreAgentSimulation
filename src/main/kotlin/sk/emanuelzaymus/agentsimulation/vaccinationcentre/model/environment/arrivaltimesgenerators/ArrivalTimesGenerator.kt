package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment.arrivaltimesgenerators

import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.NOT_ARRIVING_PATIENTS_MAX
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.NOT_ARRIVING_PATIENTS_MIN

abstract class ArrivalTimesGenerator {

    protected val notArrivingRng = UniformContinuousRNG(NOT_ARRIVING_PATIENTS_MIN, NOT_ARRIVING_PATIENTS_MAX)

    abstract fun generateArrivalTimes(numberOfPatients: Int): List<Double>

}