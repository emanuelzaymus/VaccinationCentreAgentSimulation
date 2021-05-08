package sk.emanuelzaymus.agentsimulation.view.fragments

import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.roomdata.OverallData
import tornadofx.*

class OverallStatistics : Fragment("Overall Statistics") {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val data: OverallData by param()

    override val root = hbox {
        vbox {
            hbox {
                addClass(Styles.biggerPaddingHeading)
                label("All Replications Statistics") { addClass(Styles.bigHeading) }
            }
            hbox(largeSpaces) {
                addClass(Styles.biggerPadding)
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    label("Overall Patient waiting time") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("In hours:")
                        label(data.waitingAvgInHours)
                    }
                    hbox(smallSpaces) {
                        label("In seconds:")
                        label(data.waitingAvg)
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label(data.waitingLower)
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label(data.waitingUpper)
                    }
                    label("Average worker's workload") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Workload:")
                        label(data.workloadAvg)
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label(data.workloadLower)
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label(data.workloadUpper)
                    }
                }
            }
        }
    }

}
