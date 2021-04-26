package sk.emanuelzaymus.agentsimulation.vaccinationcentre

class MessageCodes {

    companion object {
        const val init = 100

        const val patientArrival = 1
        const val patientLeaving = 2

        const val patientRegistration = 3
        const val patientRegistrationDone = 4

        const val registrationEnd = 5
        const val getNewPatient = 6

        const val examination = 7
        const val examinationDone = 8

        const val vaccination = 9
        const val vaccinationDone = 10

        const val waiting = 11
        const val waitingDone = 12
    }

}