package sk.emanuelzaymus.agentsimulation.controller.roomdata

import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import sk.emanuelzaymus.agentsimulation.controller.workerdata.WorkersBreakData
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.IWorkersRoomAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker
import tornadofx.observableList

class LunchBreakData {

    val workers = observableList<WorkersBreakData>()
    val workersCount = SimpleIntegerProperty(0)

    fun refresh(agent: IWorkersRoomAgent<VaccinationCentreWorker>) = Platform.runLater {
        workers.clear()
        workers.addAll(agent.convertWorkers { WorkersBreakData.create(it) })

        workersCount.value = workers.size
    }

}