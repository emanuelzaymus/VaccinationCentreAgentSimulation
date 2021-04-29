package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPABA.IdList

class MessageCodes {

    companion object {
        const val init = 100

        const val scheduleArrival = 1
        const val patientArrival = 2

        const val registrationStart = 3
        const val registrationEnd = 4

        const val examinationStart = 5
        const val examinationEnd = 6

        const val vaccinationStart = 7
        const val vaccinationEnd = 8

        const val waitingStart = 9
        const val waitingEnd = 10

//        const val transferEnd = 11

        const val injectionsPreparationStart = 11
        const val injectionsPreparationEnd = 12

        const val examinationTransferStart = 15
        const val examinationTransferEnd = 16

        const val vaccinationTransferStart = 17
        const val vaccinationTransferEnd = 18

        const val waitingTransferStart = 19
        const val waitingTransferEnd = 20

        const val patientLeaving = 50

        fun getName(messageCode: Int): String = when (messageCode) {
            init -> "init"
            scheduleArrival -> "scheduleArrival"
            patientArrival -> "patientArrival"
            registrationStart -> "registrationStart"
            registrationEnd -> "registrationEnd"
            examinationStart -> "examinationStart"
            examinationEnd -> "examinationEnd"
            vaccinationStart -> "vaccinationStart"
            vaccinationEnd -> "vaccinationEnd"
            waitingStart -> "waitingStart"
            waitingEnd -> "waitingEnd"
            injectionsPreparationStart -> "injectionsPreparationStart"
            injectionsPreparationEnd -> "injectionsPreparationEnd"

            examinationTransferStart -> "examinationTransferStart"
            examinationTransferEnd -> "examinationTransferEnd"
            vaccinationTransferStart -> "vaccinationTransferStart"
            vaccinationTransferEnd -> "vaccinationTransferEnd"
            waitingTransferStart -> "waitingTransferStart"
            waitingTransferEnd -> "waitingTransferEnd"

            patientLeaving -> "patientLeaving"

            IdList.start -> "start"
            IdList.finish -> "finish"

            else -> throw IllegalArgumentException("Unknown message code.")
        }
    }

}