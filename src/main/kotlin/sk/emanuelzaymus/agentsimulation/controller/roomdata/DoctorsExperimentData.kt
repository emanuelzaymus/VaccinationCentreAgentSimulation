package sk.emanuelzaymus.agentsimulation.controller.roomdata

import javafx.application.Platform
import javafx.scene.chart.XYChart
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation
import tornadofx.observableList

class DoctorsExperimentData {

    val doctorsExamQueueChartData = observableList<XYChart.Data<Number, Number>>()

    fun refresh(sim: VaccinationCentreAgentSimulation) = Platform.runLater {
        doctorsExamQueueChartData.add(XYChart.Data(sim.numberOfDoctors, sim.examinationStats.queueLengthStat.mean()))
    }

    fun clearChartData() = doctorsExamQueueChartData.clear()

}