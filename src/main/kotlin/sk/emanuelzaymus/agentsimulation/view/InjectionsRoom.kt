package sk.emanuelzaymus.agentsimulation.view

import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.InjectionsPrepRoomData
import tornadofx.*

class InjectionsRoom : Fragment("Injections Preparation") {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val room: InjectionsPrepRoomData by param()

    override val root = hbox {
        vbox {
            hbox {
                addClass(Styles.biggerPaddingHeading)
                label("Current Replication Statistics") { addClass(Styles.bigHeading) }
            }
            hbox(largeSpaces) {
                addClass(Styles.biggerPadding)
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    label("Injections Preparation Room") { addClass(Styles.smallHeading) }
                    vbox(smallSpaces) {
                        label("Actual waiting nurses:")
                        label(room.queueActualLength)
                    }
                    vbox(smallSpaces) {
                        label("Average waiting nurses:")
                        label(room.queueAvgLength)
                    }
                }
            }
        }

        vbox {
            hbox {
                addClass(Styles.biggerPaddingHeading)
                label("All Replications Statistics") { addClass(Styles.bigHeading) }
            }
            hbox(largeSpaces) {
                addClass(Styles.biggerPadding)
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    label("Injection Preparation Room") { addClass(Styles.smallHeading) }
                    vbox(smallSpaces) {
                        label("Average waiting nurses:")
                        label(room.allQueueAvgLength)
                    }
                    label("95 % Confidence Interval") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Lower bound:")
                        label(room.allQueueAvgLenLower)
                    }
                    hbox(smallSpaces) {
                        label("Upper bound:")
                        label(room.allQueueAvgLenUpper)
                    }
                }
            }
        }
    }

}
