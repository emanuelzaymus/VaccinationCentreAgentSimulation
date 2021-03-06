package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment.arrivaltimesgenerators

import OSPRNG.EmpiricPair
import OSPRNG.EmpiricRNG
import OSPRNG.UniformContinuousRNG
import OSPRNG.UniformDiscreteRNG
import sk.emanuelzaymus.agentsimulation.utils.minToSec
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.NORMAL_PATIENT_COUNT
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.WORKING_TIME

object EarlyArrivalTimesGenerator : ArrivalTimesGenerator() {

    private val earlyArrivals = EmpiricRNG(
        EmpiricPair(UniformContinuousRNG(minToSec(1), minToSec(20)), 0.3),
        EmpiricPair(UniformContinuousRNG(minToSec(20), minToSec(60)), 0.4),
        EmpiricPair(UniformContinuousRNG(minToSec(60), minToSec(80)), 0.2),
        EmpiricPair(UniformContinuousRNG(minToSec(80), minToSec(240)), 0.1)
    )

    /**
     * Generates patient arrivals from which 90% will be earlier than it should by empirical distribution.
     */
    override fun generateArrivalTimes(numberOfPatients: Int): List<Double> {
        val patientNumberRng = UniformDiscreteRNG(0, numberOfPatients)
        val notArrivingCount = (super.notArrivingRng.sample() * (numberOfPatients / NORMAL_PATIENT_COUNT)).toInt()
        val betweenArrivalsDuration: Double = WORKING_TIME / numberOfPatients
        val willBeEarly = UniformContinuousRNG(.0, 1.0)

        val arrivals = mutableListOf<Double>()
        for (i in 0 until numberOfPatients) {
            // Is coming?
            if (patientNumberRng.sample() >= notArrivingCount) {
                val nextPlanedTime = i * betweenArrivalsDuration
                // Will be early?
                val realArrival: Double =
                    if (willBeEarly.sample() <= 0.9) nextPlanedTime - earlyArrivals.sample().toDouble()
                    else nextPlanedTime

                arrivals.add(if (realArrival < 0) .0 else realArrival)
            }
        }
        return arrivals.sorted()
    }

}