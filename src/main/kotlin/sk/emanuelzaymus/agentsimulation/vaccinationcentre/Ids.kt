package sk.emanuelzaymus.agentsimulation.vaccinationcentre

class Ids {

    companion object {
        const val modelAgent = 1
        const val environmentAgent = 2
        const val registrationAgent = 3
        const val examinationAgent = 4
        const val vaccinationAgent = 5
        const val waitingAgent = 6
        const val injectionsAgent = 7
        const val breakAgent = 8

        const val modelManager = 101
        const val environmentManager = 102
        const val registrationManager = 103
        const val examinationManager = 104
        const val vaccinationManager = 105
        const val waitingManager = 106
        const val injectionsManager = 107
        const val breakManager = 108

        const val patientArrivalsScheduler = 1002
        const val registrationProcess = 1003
        const val examinationProcess = 1004
        const val vaccinationProcess = 1005
        const val waitingProcess = 1006
        const val injectionsPreparationProcess = 1007

        const val examinationTransferProcess = 1008
        const val vaccinationTransferProcess = 1009
        const val waitingTransferProcess = 1010
        const val toInjectionsTransferProcess = 1011
        const val fromInjectionsTransferProcess = 1012

        const val toCanteenTransferProcess = 1013
        const val fromCanteenTransferProcess = 1014

        const val lunchProcess = 1015

        const val adminWorkersLunchBreakScheduler = 1016
        const val doctorsLunchBreakScheduler = 1017
        const val nursesLunchBreakScheduler = 1018
    }

}