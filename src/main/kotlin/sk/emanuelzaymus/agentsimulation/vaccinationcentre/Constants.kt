package sk.emanuelzaymus.agentsimulation.vaccinationcentre

// All time values are in seconds.

internal const val WORKING_TIME: Double = 9 * 60 * 60.0 // 32_400

internal const val NOT_ARRIVING_PATIENTS_MIN: Double = 5.0
internal const val NOT_ARRIVING_PATIENTS_MAX: Double = 25.0

internal const val REGISTRATION_DURATION_MIN: Double = 140.0
internal const val REGISTRATION_DURATION_MAX: Double = 220.0

internal const val EXAMINATION_DURATION_MEAN: Double = 260.0

internal const val VACCINATION_DURATION_MIN: Double = 20.0
internal const val VACCINATION_DURATION_MODE: Double = 75.0
internal const val VACCINATION_DURATION_MAX: Double = 100.0

internal const val WAITING_LESS_PROBABILITY: Double = 0.95
internal const val WAITING_DURATION_LESS: Double = 15 * 60.0 // 900
internal const val WAITING_DURATION_MORE: Double = 30 * 60.0 // 1_800

internal const val INJECTION_PREP_DURATION_MIN: Double = 6.0
internal const val INJECTION_PREP_DURATION_MODE: Double = 10.0
internal const val INJECTION_PREP_DURATION_MAX: Double = 40.0
internal const val INJECTIONS_COUNT_TO_PREPARE: Int = 2 // TODO: put 20
