package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.arrivaltimesgenerators

import OSPRNG.UniformContinuousRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

abstract class ArrivalTimesGenerator {

    protected val notArrivingRng = UniformContinuousRNG(C.NOT_ARRIVING_PATIENTS_MIN, C.NOT_ARRIVING_PATIENTS_MAX)

    abstract fun generateArrivalTimes(numberOfPatients: Int): List<Double>

}