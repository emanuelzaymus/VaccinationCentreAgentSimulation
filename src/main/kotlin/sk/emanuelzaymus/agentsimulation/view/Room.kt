package sk.emanuelzaymus.agentsimulation.view

import sk.emanuelzaymus.agentsimulation.app.Styles
import tornadofx.*

class Room : Fragment() {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val tabTitle: String by param()

    init {
        title = tabTitle
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
                    label("$tabTitle Queue") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Actual length:")
                        label("0")
                    }
                    hbox(smallSpaces) {
                        label("Average length:")
                        label("0.0")
                    }
                    label("Average waiting time")
                    hbox(smallSpaces) {
                        label("In hours:")
                        label("0:00:00.00000")
                    }
                    hbox(smallSpaces) {
                        label("In seconds:")
                        label("0")
                    }
                    label("Workers") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Busy workers:")
                        label("0")
                    }
                    hbox(smallSpaces) {
                        label("Average workload:")
                        label("0.0")
                    }
                    label("Next Stage Transit") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Count:")
                        label("5")
                    }
                }
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    label("Administrative workers") { addClass(Styles.smallHeading) }
//                tableview(mainController.regRoomPersonalWorkloads) {
                    tableview(observableList<Any>()) {
                        prefWidth = 170.0
                        prefHeight = 210.0
//                    readonlyColumn("Num", Worker::id).prefWidth = 40.0
//                    readonlyColumn("Working", Worker::working).prefWidth = 60.0
//                    readonlyColumn("Workload", Worker::avgWorkload).prefWidth = 77.0
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
                    label("$tabTitle Queue") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Average length:")
                        label("0")
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label("0.6")
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label("0.9")
                    }
                    label("Average waiting time")
                    hbox(smallSpaces) {
                        label("In hours:")
                        label("0:00:00.00000")
                    }
                    hbox(smallSpaces) {
                        label("In seconds:")
                        label("0")
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label("0.6")
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label("0.9")
                    }
                }
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    label("Administrative Workers") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Average workload:")
                        label("0.753")
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label("0.6")
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label("0.9")
                    }
                }
            }
        }
    }

}
