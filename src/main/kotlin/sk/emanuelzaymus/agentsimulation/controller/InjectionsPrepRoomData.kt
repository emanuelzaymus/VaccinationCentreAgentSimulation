package sk.emanuelzaymus.agentsimulation.controller

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections.InjectionsAgent

class InjectionsPrepRoomData {

    private val initVal = 0.0.roundToString()
    private val dash = "-"

    val queueActualLength = SimpleIntegerProperty()
    val queueAvgLength = SimpleStringProperty(initVal)

    val allQueueAvgLength = SimpleStringProperty(initVal)
    val allQueueAvgLenLower = SimpleStringProperty(dash)
    val allQueueAvgLenUpper = SimpleStringProperty(dash)

    fun refresh(agent: InjectionsAgent, allNursesQueueLengthStats: Stat) = Platform.runLater {
        queueActualLength.value = agent.queue.size
        queueAvgLength.value = agent.queueLengthMean().roundToString()

        RoomData.setStats(allNursesQueueLengthStats, allQueueAvgLength, allQueueAvgLenLower, allQueueAvgLenUpper)
    }

}