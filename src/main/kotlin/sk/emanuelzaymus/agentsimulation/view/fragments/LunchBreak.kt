package sk.emanuelzaymus.agentsimulation.view.fragments

import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.roomdata.LunchBreakData
import sk.emanuelzaymus.agentsimulation.controller.workerdata.WorkersBreakData
import tornadofx.*

class LunchBreak : Fragment("Lunch Break") {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val data: LunchBreakData by param()

    override val root = hbox {
        vbox {
            hbox {
                addClass(Styles.biggerPaddingHeading)
                label("Worker's Lunch Break") { addClass(Styles.bigHeading) }
            }
            hbox(largeSpaces) {
                addClass(Styles.biggerPadding)
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    hbox(smallSpaces) {
                        label("Workers on break:") { addClass(Styles.smallHeading) }
                        label(data.workersCount)
                    }
                }
                vbox(smallSpaces) {
                    tableview(data.workers) {
                        prefHeight = 290.0
                        readonlyColumn("Worker", WorkersBreakData::name).prefWidth = 70.0
                        readonlyColumn("Busy", WorkersBreakData::busy).prefWidth = 40.0
                        readonlyColumn("Lunch", WorkersBreakData::lunch).prefWidth = 40.0
                        readonlyColumn("Workload", WorkersBreakData::avgWorkload).prefWidth = 70.0
                        readonlyColumn("State", WorkersBreakData::state).prefWidth = 150.0
                    }
                }
            }
        }
    }

}
