package sk.emanuelzaymus.agentsimulation.app

import javafx.stage.Stage
import sk.emanuelzaymus.agentsimulation.view.MainView
import tornadofx.App

class MyApp : App(MainView::class, Styles::class) {

    override fun start(stage: Stage) {

// TODO: remove unnecessary code

//        val s = ExactArrivalTimesGenerator.generateArrivalTimes(10)
//        println(s)
//
//        Platform.exit()
//        return


        super.start(stage)

//        RNG.setSeedGen(Random(1))

//        val sim = VaccinationCentreAgentSimulation(10, 3, 4, 2, false)
//
////        val sim = VaccinationCentreAgentSimulation(450, 5, 6, 3, false)
////        val sim = VaccinationCentreAgentSimulation(2500, 14, 21, 6, false)
////        val sim = VaccinationCentreAgentSimulation(2500, 23, 28, 14, false)
//
//
//        sim.onSimulationWillStart { println("onSimulationWillStart") }
//        sim.onRefreshUI { println("onRefreshUI") }
//        sim.onPause { println("onPause") }
//        sim.onResume { println("onResume") }
//        sim.onReplicationWillStart { println("onReplicationWillStart") }
//        sim.onReplicationDidFinish { println("onReplicationDidFinish") }
//        sim.onSimulationDidFinish { println("onSimulationDidFinish") }
//
//
//        sim.simulate(1)
//
//        Platform.exit()
    }

}