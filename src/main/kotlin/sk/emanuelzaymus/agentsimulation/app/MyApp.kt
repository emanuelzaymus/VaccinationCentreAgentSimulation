package sk.emanuelzaymus.agentsimulation.app

import javafx.stage.Stage
import sk.emanuelzaymus.agentsimulation.view.MainView
import tornadofx.App

class MyApp : App(MainView::class, Styles::class) {

    override fun start(stage: Stage) {
        // TODO: remove unnecessary code
        super.start(stage)
    }

}