package sk.emanuelzaymus.agentsimulation.controller.roomdata

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.controller.secondsToTime

class OverallData {

    val waitingAvgInHours = SimpleStringProperty()
    val waitingAvg = SimpleStringProperty()
    val waitingLower = SimpleStringProperty()
    val waitingUpper = SimpleStringProperty()

    val workloadAvg = SimpleStringProperty()
    val workloadLower = SimpleStringProperty()
    val workloadUpper = SimpleStringProperty()

    fun refresh(overallWaiting: Stat, overallWorkload: Stat) {
        Platform.runLater { waitingAvgInHours.value = overallWaiting.mean().secondsToTime() }
        RoomData.setStats(overallWaiting, waitingAvg, waitingLower, waitingUpper)
        RoomData.setStats(overallWorkload, workloadAvg, workloadLower, workloadUpper)
    }

}