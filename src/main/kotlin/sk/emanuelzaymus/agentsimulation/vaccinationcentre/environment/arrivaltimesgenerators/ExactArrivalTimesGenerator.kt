package sk.emanuelzaymus.agentsimulation.vaccinationcentre.environment.arrivaltimesgenerators

import OSPRNG.UniformDiscreteRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.C

object ExactArrivalTimesGenerator : ArrivalTimesGenerator() {

    override fun generateArrivalTimes(numberOfPatients: Int): List<Double> {
        val patientNumberRng = UniformDiscreteRNG(0, numberOfPatients)
        val betweenArrivalsDuration = C.WORKING_TIME / numberOfPatients
        val notArrivingCount = (super.notArrivingRng.sample() * (numberOfPatients / C.NORMAL_PATIENT_COUNT)).toInt()

        val arrivals: List<Double> = List(numberOfPatients) { it * betweenArrivalsDuration }

        return arrivals.filter { patientNumberRng.sample() >= notArrivingCount }
    }

}