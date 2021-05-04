package sk.emanuelzaymus.agentsimulation.controller

import OSPABA.Simulation
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

class WaitingRoomData {

    private val initVal = 0.0.roundToString()

    val waitRoomPatientsCount = SimpleIntegerProperty()
    val waitRoomAvgLength = SimpleStringProperty(initVal)

    private val dash = "-"
    val allWaitRoomAvgLength = SimpleStringProperty(initVal)
    val lowerBoundConfInterval = SimpleStringProperty(dash)
    val upperBoundConfInterval = SimpleStringProperty(dash)

    fun refreshUI(sim: Simulation) = Platform.runLater {

    }

}