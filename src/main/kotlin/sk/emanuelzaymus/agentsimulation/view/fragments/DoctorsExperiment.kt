package sk.emanuelzaymus.agentsimulation.view.fragments

import javafx.scene.chart.NumberAxis
import sk.emanuelzaymus.agentsimulation.controller.roomdata.DoctorsExperimentData
import tornadofx.*

class DoctorsExperiment : Fragment("Doctors Experiment") {

    val doctorsData: DoctorsExperimentData by param()

    override val root = hbox {
        val xAxis = NumberAxis()
        xAxis.label = "Number of Doctors"
        val yAxis = NumberAxis()
        yAxis.label = "Avg. Exam. Queue Length"
        linechart("Doctors - Examination Queue Length Experiment", xAxis, yAxis) {
            series("Patient counts", doctorsData.doctorsExamQueueChartData)
            isLegendVisible = false
        }
    }

}
