package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.IdList

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

        fun getName(messageCode: Int): String = when (messageCode) {
            init -> "init"
            getNewPatient -> "getNewPatient"
            patientArrival -> "patientArrival"
            registration -> "registration"
            registrationDone -> "registrationDone"
            examination -> "examination"
            examinationDone -> "examinationDone"
            vaccination -> "vaccination"
            vaccinationDone -> "vaccinationDone"
            waiting -> "waiting"
            waitingDone -> "waitingDone"
            patientLeaving -> "patientLeaving"

            IdList.start -> "start"
            IdList.finish -> "finish"

            else -> throw IllegalArgumentException("Unknown message code.")
        }
    }

}