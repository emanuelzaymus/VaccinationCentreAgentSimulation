package sk.emanuelzaymus.agentsimulation.controller.roomdata

import OSPStat.Stat
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination.injections.InjectionsAgent

class InjectionsRoomData(tabTitle: String, peopleName: String) : CountRoomData(tabTitle, peopleName) {

    val vaccinesInPackageLeft = SimpleIntegerProperty()

    fun refresh(agent: InjectionsAgent, allStats: Stat) {
        super.refresh(agent, allStats)

        Platform.runLater {
            vaccinesInPackageLeft.value = agent.vaccinesInPackageLeft
        }
    }

}