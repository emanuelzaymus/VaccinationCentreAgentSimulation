package sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants

import OSPABA.IdList

object MessageCodes {

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

    const val injectionsPreparationStart = 11
    const val injectionsPreparationEnd = 12

    const val lunchBreakNow = 13
    const val lunchBreakStart = 14
    const val lunchBreakEnd = 15
    const val lunchEnd = 16

    const val examinationTransferStart = 17
    const val examinationTransferEnd = 18
    const val vaccinationTransferStart = 19
    const val vaccinationTransferEnd = 20
    const val waitingTransferStart = 21
    const val waitingTransferEnd = 22

    const val openingNewPackageEnd = 23

    const val transferEnd = 98

    const val patientLeaving = 99

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

        lunchBreakNow -> "lunchBreakNow"
        lunchBreakStart -> "lunchBreakStart"
        lunchBreakEnd -> "lunchBreakEnd"
        lunchEnd -> "lunchEnd"

        examinationTransferStart -> "examinationTransferStart"
        examinationTransferEnd -> "examinationTransferEnd"
        vaccinationTransferStart -> "vaccinationTransferStart"
        vaccinationTransferEnd -> "vaccinationTransferEnd"
        waitingTransferStart -> "waitingTransferStart"
        waitingTransferEnd -> "waitingTransferEnd"

        openingNewPackageEnd -> "openingNewPackageEnd"

        transferEnd -> "transferEnd"

        patientLeaving -> "patientLeaving"

        IdList.start -> "start"
        IdList.finish -> "finish"

        else -> throw IllegalArgumentException("Unknown message code.")
    }

}