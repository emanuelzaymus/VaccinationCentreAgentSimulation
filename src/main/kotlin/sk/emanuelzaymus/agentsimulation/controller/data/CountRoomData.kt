package sk.emanuelzaymus.agentsimulation.controller.data

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.controller.roundToString
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.ICountRoomAgent

class CountRoomData(val tabTitle: String, val peopleName: String) {

    val actualCount = SimpleIntegerProperty()
    val averageCount = SimpleStringProperty(RoomData.init0)

    val allAvgCount = SimpleStringProperty(RoomData.init0)
    val allAvgCountLower = SimpleStringProperty(RoomData.dash)
    val allAvgCountUpper = SimpleStringProperty(RoomData.dash)

    fun refresh(agent: ICountRoomAgent, allStats: Stat) = Platform.runLater {
        actualCount.value = agent.actualCount
        averageCount.value = agent.averageCount.roundToString()

        RoomData.setStats(allStats, allAvgCount, allAvgCountLower, allAvgCountUpper)
    }

}