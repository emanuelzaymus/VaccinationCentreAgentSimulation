package sk.emanuelzaymus.agentsimulation.view

import javafx.collections.ObservableList
import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.NurseData
import sk.emanuelzaymus.agentsimulation.controller.RoomData
import sk.emanuelzaymus.agentsimulation.controller.WorkerData
import tornadofx.*

class Room : Fragment() {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    val room: RoomData<*> by param()

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
                    label("${room.tabTitle} Queue") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Actual length:")
                        label(room.queueActualLength)
                    }
                    hbox(smallSpaces) {
                        label("Average length:")
                        label(room.queueAvgLength)
                    }
                    label("Average waiting time")
                    hbox(smallSpaces) {
                        label("In hours:")
                        label(room.queueAvgWaitingTimeInHours)
                    }
                    hbox(smallSpaces) {
                        label("In seconds:")
                        label(room.queueAvgWaitingTime)
                    }
                    label(room.workers) { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Busy workers:")
                        label(room.busyWorkers)
                    }
                    hbox(smallSpaces) {
                        label("Average workload:")
                        label(room.workload)
                    }
                    label("Next Stage Transfer") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Count:")
                        label(room.nextStageTransferCount)
                    }
                }
                vbox(smallSpaces) {
                    label(room.workers) { addClass(Styles.smallHeading) }
                    if (room.isNurse) {
                        @Suppress("UNCHECKED_CAST")
                        tableview(room.personalWorkloads as ObservableList<NurseData>) {
                            prefHeight = 260.0
                            readonlyColumn("Num", NurseData::id).prefWidth = 40.0
                            readonlyColumn("Busy", NurseData::busy).prefWidth = 40.0
                            readonlyColumn("Workload", NurseData::avgWorkload).prefWidth = 70.0
                            readonlyColumn("Injections", NurseData::injectionsLeft).prefWidth = 70.0
                            readonlyColumn("State", NurseData::state).prefWidth = 150.0
                        }
                    } else {
                        @Suppress("UNCHECKED_CAST")
                        tableview(room.personalWorkloads as ObservableList<WorkerData>) {
                            prefHeight = 260.0
                            readonlyColumn("Num", WorkerData::id).prefWidth = 40.0
                            readonlyColumn("Busy", WorkerData::busy).prefWidth = 40.0
                            readonlyColumn("Workload", WorkerData::avgWorkload).prefWidth = 70.0
                            readonlyColumn("State", WorkerData::state).prefWidth = 150.0
                        }
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
                    label("${room.tabTitle} Queue") { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Average length:")
                        label(room.allQueueAvgLength)
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label(room.allQueueAvgLenLower)
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label(room.allQueueAvgLenUpper)
                    }
                    label("Average waiting time")
                    hbox(smallSpaces) {
                        label("In hours:")
                        label(room.allQueueAvgWaitingTimeInHours)
                    }
                    hbox(smallSpaces) {
                        label("In seconds:")
                        label(room.allQueueAvgWaitingTime)
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label(room.allQueueAvgWaitLower)
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label(room.allQueueAvgWaitUpper)
                    }
                }
                vbox(smallSpaces) {
                    prefWidth = preferredWidth
                    label(room.workers) { addClass(Styles.smallHeading) }
                    hbox(smallSpaces) {
                        label("Average workload:")
                        label(room.allWorkload)
                    }
                    hbox(smallSpaces) {
                        label("95% lower bound:")
                        label(room.allWorkloadLower)
                    }
                    hbox(smallSpaces) {
                        label("95% upper bound:")
                        label(room.allWorkloadUpper)
                    }
                }
            }
        }
    }

}
