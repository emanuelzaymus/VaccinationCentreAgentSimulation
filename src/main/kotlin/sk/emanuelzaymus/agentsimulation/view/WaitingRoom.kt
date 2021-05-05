package sk.emanuelzaymus.agentsimulation.view

import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.data.WaitingRoomData
import tornadofx.*

class WaitingRoom : Fragment("Waiting Room") {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val room: WaitingRoomData by param()

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
                    label("Waiting Room") { addClass(Styles.smallHeading) }
                    vbox(smallSpaces) {
                        label("Actual waiting patients:")
                        label(room.waitRoomPatientsCount)
                    }
                    vbox(smallSpaces) {
                        label("Average waiting patients:")
                        label(room.waitRoomAvgLength)
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
                    label("Waiting Room") { addClass(Styles.smallHeading) }
                    vbox(smallSpaces) {
                        label("Average waiting patients:")
                        label(room.allWaitRoomAvgLength)
                    }
                    label("95 % Confidence Interval") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Lower bound:")
                        label(room.lowerBoundConfInterval)
                    }
                    hbox(smallSpaces) {
                        label("Upper bound:")
                        label(room.upperBoundConfInterval)
                    }
                }
            }
        }
    }

}
