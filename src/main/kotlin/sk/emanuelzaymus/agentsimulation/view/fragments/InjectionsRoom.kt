package sk.emanuelzaymus.agentsimulation.view.fragments

import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.roomdata.InjectionsRoomData
import tornadofx.*

class InjectionsRoom : Fragment() {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val room: InjectionsRoomData by param()

    init {
        title = room.tabTitle
    }

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
                    label(room.tabTitle) { addClass(Styles.smallHeading) }
                    vbox(smallSpaces) {
                        label("Actual waiting ${room.peopleName}:")
                        label(room.actualCount)
                    }
                    vbox(smallSpaces) {
                        label("Average waiting ${room.peopleName}:")
                        label(room.averageCount)
                    }
                    vbox(smallSpaces) {
                        label("Actual vaccines in package left:")
                        label(room.vaccinesInPackageLeft)
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
                    label(room.tabTitle) { addClass(Styles.smallHeading) }
                    vbox(smallSpaces) {
                        label("Average waiting ${room.peopleName}:")
                        label(room.allAvgCount)
                    }
                    label("95 % Confidence Interval") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Lower bound:")
                        label(room.allAvgCountLower)
                    }
                    hbox(smallSpaces) {
                        label("Upper bound:")
                        label(room.allAvgCountUpper)
                    }
                }
            }
        }
    }

}
