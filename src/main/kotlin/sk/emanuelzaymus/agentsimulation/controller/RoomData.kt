package sk.emanuelzaymus.agentsimulation.controller

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.AgentStats
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.VaccinationCentreActivityAgent
import tornadofx.observableList

class RoomData<T : WorkerData>(
    val tabTitle: String, val workers: String, val isNurse: Boolean = false, val init: (w: VaccinationCentreWorker) -> T
) {

    private val initVal = 0.0.roundToString()
    private val dash = "-"

    val queueActualLength = SimpleIntegerProperty()
    val queueAvgLength = SimpleStringProperty(initVal)
    val queueAvgWaitingTimeInHours = SimpleStringProperty(initVal)
    val queueAvgWaitingTime = SimpleStringProperty(initVal)

    val busyWorkers = SimpleIntegerProperty()
    val workload = SimpleStringProperty(initVal)
    val nextStageTransferCount = SimpleIntegerProperty()

    val personalWorkloads = observableList<T>()

    val allQueueAvgLength = SimpleStringProperty(initVal)
    val allQueueAvgLenLower = SimpleStringProperty(dash)
    val allQueueAvgLenUpper = SimpleStringProperty(dash)

    val allQueueAvgWaitingTimeInHours = SimpleStringProperty(initVal)
    val allQueueAvgWaitingTime = SimpleStringProperty(initVal)
    val allQueueAvgWaitLower = SimpleStringProperty(dash)
    val allQueueAvgWaitUpper = SimpleStringProperty(dash)

    val allWorkload = SimpleStringProperty(initVal)
    val allWorkloadLower = SimpleStringProperty(dash)
    val allWorkloadUpper = SimpleStringProperty(dash)

    fun refresh(agent: VaccinationCentreActivityAgent<*>, agentStats: AgentStats, nextStageTransferCount: Int) {
        refresh(agent)
        refresh(agentStats)
        setNextStageTransferCount(nextStageTransferCount)
    }

    private fun refresh(agent: VaccinationCentreActivityAgent<*>) = Platform.runLater {
        queueActualLength.value = agent.queue.size
        queueAvgLength.value = agent.queue.lengthStatistic().mean().roundToString()
        queueAvgWaitingTimeInHours.value = agent.waitingTimeStat.mean().secondsToTime()
        queueAvgWaitingTime.value = agent.waitingTimeStat.mean().roundToString()
        busyWorkers.value = agent.workers.count { it.isBusy }
        workload.value = agent.workers.map { it.workloadStat.mean() }.average().roundToString()

        personalWorkloads.clear()
        personalWorkloads.addAll(agent.workers.map { init(it) })// T(it.id, it.isBusy, it.workloadStat.mean(), it.state.name) })
    }

    private fun refresh(agentStats: AgentStats) {
        setStats(agentStats.queueLengthStat, allQueueAvgLength, allQueueAvgLenLower, allQueueAvgLenUpper)
        setQWaitTimeInHours(agentStats.waitingTimeStat.mean())
        setStats(agentStats.waitingTimeStat, allQueueAvgWaitingTime, allQueueAvgWaitLower, allQueueAvgWaitUpper)
        setStats(agentStats.workersWorkload, allWorkload, allWorkloadLower, allWorkloadUpper)
    }

    private fun setStats(
        stats: Stat, avg: SimpleStringProperty, lower: SimpleStringProperty, upper: SimpleStringProperty
    ) = Platform.runLater {
        avg.value = stats.mean().roundToString()
        if (stats.sampleSize() >= 2) {
            val confidenceInterval = stats.confidenceInterval_95()
            lower.value = confidenceInterval[0].roundToString()
            upper.value = confidenceInterval[1].roundToString()
        }
    }

    private fun setQWaitTimeInHours(timeInSeconds: Double) = Platform.runLater {
        allQueueAvgWaitingTimeInHours.value = timeInSeconds.secondsToTime()
    }

    private fun setNextStageTransferCount(count: Int) = Platform.runLater {
        nextStageTransferCount.value = count
    }

}