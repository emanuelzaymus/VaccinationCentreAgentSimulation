package sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants

// All time values are in seconds.

private const val START_HOUR: Int = 8

const val WORKING_TIME: Double = 9 * 60 * 60.0 // 32_400

const val NORMAL_PATIENT_COUNT: Int = 540
const val NOT_ARRIVING_PATIENTS_MIN: Double = 5.0
const val NOT_ARRIVING_PATIENTS_MAX: Double = 25.0

const val REGISTRATION_DURATION_MIN: Double = 140.0
const val REGISTRATION_DURATION_MAX: Double = 220.0

const val EXAMINATION_DURATION_MEAN: Double = 260.0

const val VACCINATION_DURATION_MIN: Double = 20.0
const val VACCINATION_DURATION_MODE: Double = 75.0
const val VACCINATION_DURATION_MAX: Double = 100.0

const val WAITING_LESS_PROBABILITY: Double = 0.95
const val WAITING_DURATION_LESS: Double = 15 * 60.0 // 900
const val WAITING_DURATION_MORE: Double = 30 * 60.0 // 1_800

const val INJECTION_PREP_DURATION_MIN: Double = 6.0
const val INJECTION_PREP_DURATION_MODE: Double = 10.0
const val INJECTION_PREP_DURATION_MAX: Double = 40.0
const val INJECTIONS_COUNT_TO_PREPARE: Int = 20
const val INJECTIONS_COUNT_IN_PACKAGE: Int = 400

const val EXAMINATION_TRANSFER_DURATION_MIN: Double = 40.0
const val EXAMINATION_TRANSFER_DURATION_MAX: Double = 90.0

const val VACCINATION_TRANSFER_DURATION_MIN: Double = 20.0
const val VACCINATION_TRANSFER_DURATION_MAX: Double = 45.0

const val WAITING_TRANSFER_DURATION_MIN: Double = 45.0
const val WAITING_TRANSFER_DURATION_MAX: Double = 110.0

const val INJECTIONS_TRANSFER_DURATION_MIN: Double = 10.0
const val INJECTIONS_TRANSFER_DURATION_MAX: Double = 18.0

const val CANTEEN_TRANSFER_DURATION_MIN: Double = 70.0
const val CANTEEN_TRANSFER_DURATION_MAX: Double = 200.0

const val LUNCH_DURATION_MIN: Double = 5 * 60.0 // 300
const val LUNCH_DURATION_MODE: Double = 15 * 60.0 // 900
const val LUNCH_DURATION_MAX: Double = 30 * 60.0 // 1_800

const val ADMIN_WORKERS_LUNCH_BREAK_START: Double = (11 - START_HOUR) * 60 * 60.0 // 10_800 // 11:00
const val DOCTORS_LUNCH_BREAK_START: Double = (11 - START_HOUR) * 60 * 60 + 45 * 60.0 // 13_500 // 11:45
const val NURSES_LUNCH_BREAK_START: Double = (13 - START_HOUR) * 60 * 60 + 30 * 60.0  // 19_800 // 13:30