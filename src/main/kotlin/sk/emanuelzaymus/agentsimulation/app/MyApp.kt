package sk.emanuelzaymus.agentsimulation.app

import javafx.application.Platform
import javafx.stage.Stage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.GasStationSimulation
import sk.emanuelzaymus.agentsimulation.view.MainView
import tornadofx.App

class MyApp : App(MainView::class, Styles::class) {

    override fun start(stage: Stage) {
//        super.start(stage)

        val sim = GasStationSimulation()
        sim.onSimulationWillStart { println("Simulating...") }
        sim.simulate(3, 9000000.0)

        Platform.exit()
    }

}