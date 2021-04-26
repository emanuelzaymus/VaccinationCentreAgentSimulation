package sk.emanuelzaymus.agentsimulation.vaccinationcentre

class MessageCodes {

    companion object {
        const val init = 100

        const val getNewPatient = 1
        const val patientArrival = 2

        const val registration = 3
        const val registrationDone = 4

        const val examination = 5
        const val examinationDone = 6

        const val vaccination = 7
        const val vaccinationDone = 8

        const val waiting = 9
        const val waitingDone = 10

        const val patientLeaving = 11
    }

}