package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

enum class WorkerState {
    FREE,
    WORKING,
    GOING_TO_LUNCH,
    DINING,
    GOING_FROM_LUNCH,

    GOING_TO_PREPARE_INJECTIONS,
    WAITING_TO_INJECTIONS_PREPARATION,
    PREPARING_INJECTIONS,
    GOING_FROM_INJECTIONS_PREPARATION;

    fun isBusy(): Boolean = this != FREE

    fun isHavingLunchBreak(): Boolean = this in listOf(GOING_TO_LUNCH, DINING, GOING_FROM_LUNCH)

}