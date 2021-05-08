package sk.emanuelzaymus.agentsimulation.controller.roomdata

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.controller.roundToString
import sk.emanuelzaymus.agentsimulation.controller.secondsToTime
import sk.emanuelzaymus.agentsimulation.controller.workerdata.WorkerData
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.AgentStats
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.IStatisticsAgent
import tornadofx.observableList

class RoomData<T : WorkerData>(
    val tabTitle: String, val workers: String, val isNurse: Boolean = false, val init: (VaccinationCentreWorker) -> T
) {
    companion object {
        const val dash = "-"
        val init0 = 0.0.roundToString()

        fun setStats(
            stats: Stat, avg: SimpleStringProperty, lower: SimpleStringProperty, upper: SimpleStringProperty
        ) = Platform.runLater {
            avg.value = stats.mean().roundToString()
            if (stats.sampleSize() >= 2) {
                val confidenceInterval = stats.confidenceInterval_95()
                lower.value = confidenceInterval[0].roundToString()
                upper.value = confidenceInterval[1].roundToString()
            }
        }
    }

    val queueActualLength = SimpleIntegerProperty()
    val queueAvgLength = SimpleStringProperty(init0)
    val queueAvgWaitingTimeInHours = SimpleStringProperty(init0)
    val queueAvgWaitingTime = SimpleStringProperty(init0)

    val busyWorkers = SimpleIntegerProperty()
    val workload = SimpleStringProperty(init0)
    val nextStageTransferCount = SimpleIntegerProperty()

    val personalWorkloads = observableList<T>()

    val allQueueAvgLength = SimpleStringProperty(init0)
    val allQueueAvgLenLower = SimpleStringProperty(dash)
    val allQueueAvgLenUpper = SimpleStringProperty(dash)

    val allQueueAvgWaitingTimeInHours = SimpleStringProperty(init0)
    val allQueueAvgWaitingTime = SimpleStringProperty(init0)
    val allQueueAvgWaitLower = SimpleStringProperty(dash)
    val allQueueAvgWaitUpper = SimpleStringProperty(dash)

    val allWorkload = SimpleStringProperty(init0)
    val allWorkloadLower = SimpleStringProperty(dash)
    val allWorkloadUpper = SimpleStringProperty(dash)

    fun refresh(agent: IStatisticsAgent<*>, agentStats: AgentStats, nextStageTransferCount: Int) {
        refresh(agent)
        refresh(agentStats)
        setNextStageTransferCount(nextStageTransferCount)
    }

    private fun refresh(agent: IStatisticsAgent<*>) = Platform.runLater {
        queueActualLength.value = agent.actualQueueLength
        queueAvgLength.value = agent.averageQueueLength.roundToString()
        queueAvgWaitingTimeInHours.value = agent.averageWaitingTime.secondsToTime()
        queueAvgWaitingTime.value = agent.averageWaitingTime.roundToString()
        busyWorkers.value = agent.busyWorkersCount
        workload.value = agent.averageWorkload.roundToString()

        personalWorkloads.clear()
        personalWorkloads.addAll(agent.convertWorkers(init))
    }

    private fun refresh(agentStats: AgentStats) {
        setStats(agentStats.queueLengthStat, allQueueAvgLength, allQueueAvgLenLower, allQueueAvgLenUpper)
        setQWaitTimeInHours(agentStats.waitingTimeStat.mean())
        setStats(agentStats.waitingTimeStat, allQueueAvgWaitingTime, allQueueAvgWaitLower, allQueueAvgWaitUpper)
        setStats(agentStats.workersWorkload, allWorkload, allWorkloadLower, allWorkloadUpper)
    }

    private fun setQWaitTimeInHours(timeInSeconds: Double) = Platform.runLater {
        allQueueAvgWaitingTimeInHours.value = timeInSeconds.secondsToTime()
    }

    private fun setNextStageTransferCount(count: Int) = Platform.runLater {
        nextStageTransferCount.value = count
    }

}