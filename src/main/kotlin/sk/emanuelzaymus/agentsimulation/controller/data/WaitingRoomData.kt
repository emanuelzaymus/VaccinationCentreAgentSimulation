package sk.emanuelzaymus.agentsimulation.controller.data

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.controller.roundToString
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.waiting.WaitingAgent

class WaitingRoomData {

    private val initVal = 0.0.roundToString()
    private val dash = "-"

    val waitRoomPatientsCount = SimpleIntegerProperty()
    val waitRoomAvgLength = SimpleStringProperty(initVal)

    val allWaitRoomAvgLength = SimpleStringProperty(initVal)
    val lowerBoundConfInterval = SimpleStringProperty(dash)
    val upperBoundConfInterval = SimpleStringProperty(dash)

    fun refresh(agent: WaitingAgent, allStats: Stat) = Platform.runLater {
        waitRoomPatientsCount.value = agent.waitingPatients
        waitRoomAvgLength.value = agent.getWaitingPatientsCountMean().roundToString()

        RoomData.setStats(allStats, allWaitRoomAvgLength, lowerBoundConfInterval, upperBoundConfInterval)
    }

}