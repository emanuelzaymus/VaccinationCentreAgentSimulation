package sk.emanuelzaymus.agentsimulation.controller

/**
 * Worker data class with workers id, state-isWorking and workload.
 */
data class Worker(
    val id: Int,
    private val isBusy: Boolean,
    private val workload: Double,
    val state: String
) {
    val busy: String get() = if (isBusy) "Yes" else "No"
    val avgWorkload get() = workload.roundToString()
}