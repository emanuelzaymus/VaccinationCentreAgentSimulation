package sk.emanuelzaymus.agentsimulation.vaccinationcentre

class MessageCodes {

    companion object {
        const val init = 7

        const val patientArrival = 1
        const val patientLeaving = 2
        const val patientRegistration = 3
        const val patientRegistrationDone = 4

        const val registrationEnd = 5
        const val newPatient = 6
    }

}