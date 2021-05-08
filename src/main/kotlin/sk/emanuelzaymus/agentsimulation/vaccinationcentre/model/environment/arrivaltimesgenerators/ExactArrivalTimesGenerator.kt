package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.environment.arrivaltimesgenerators

import OSPRNG.UniformDiscreteRNG
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.NORMAL_PATIENT_COUNT
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.WORKING_TIME

object ExactArrivalTimesGenerator : ArrivalTimesGenerator() {

    override fun generateArrivalTimes(numberOfPatients: Int): List<Double> {
        val patientNumberRng = UniformDiscreteRNG(0, numberOfPatients)
        val betweenArrivalsDuration = WORKING_TIME / numberOfPatients
        val notArrivingCount = (super.notArrivingRng.sample() * (numberOfPatients / NORMAL_PATIENT_COUNT)).toInt()

        val arrivals: List<Double> = List(numberOfPatients) { it * betweenArrivalsDuration }

        return arrivals.filter { patientNumberRng.sample() >= notArrivingCount }
    }

}