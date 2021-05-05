package sk.emanuelzaymus.agentsimulation.controller

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting.WaitingAgent

class WaitingRoomData {

    private val initVal = 0.0.roundToString()

    val waitRoomPatientsCount = SimpleIntegerProperty()
    val waitRoomAvgLength = SimpleStringProperty(initVal)

    private val dash = "-"
    val allWaitRoomAvgLength = SimpleStringProperty(initVal)
    val lowerBoundConfInterval = SimpleStringProperty(dash)
    val upperBoundConfInterval = SimpleStringProperty(dash)

    fun refresh(agent: WaitingAgent, allStats: Stat) = Platform.runLater {
        waitRoomPatientsCount.value = agent.waitingPatients
        waitRoomAvgLength.value = agent.getWaitingPatientsCountMean().roundToString()

        allWaitRoomAvgLength.value = allStats.mean().roundToString()
        if (allStats.sampleSize() >= 2) {
            val confidenceInterval = allStats.confidenceInterval_95()
            lowerBoundConfInterval.value = confidenceInterval[0].roundToString()
            upperBoundConfInterval.value = confidenceInterval[1].roundToString()
        }
    }

}