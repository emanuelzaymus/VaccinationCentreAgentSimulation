package sk.emanuelzaymus.agentsimulation.app

import OSPRNG.RNG
import javafx.application.Platform
import javafx.stage.Stage
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation
import sk.emanuelzaymus.agentsimulation.view.MainView
import tornadofx.App
import java.util.*

class MyApp : App(MainView::class, Styles::class) {

    override fun start(stage: Stage) {
//        super.start(stage)

        RNG.setSeedGen(Random(1))

        val sim = VaccinationCentreAgentSimulation()
        sim.onSimulationWillStart { println("Simulating...") }
        sim.simulate(3)

        Platform.exit()
    }

}