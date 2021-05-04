package sk.emanuelzaymus.agentsimulation.view

import javafx.scene.control.CheckBox
import javafx.scene.control.TabPane
import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.MainController
import tornadofx.*

class MainView : View("Vaccination Centre Agent Simulation") {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    private val controller: MainController by inject()

    private lateinit var withAnimationCheckBox: CheckBox

    override val root = vbox {
        hbox(largeSpaces) {
            addClass(Styles.biggerPadding)
            gridpane {
                vgap = smallSpaces.toDouble()
                hgap = smallSpaces.toDouble()
                row {
                    label("Replications count:")
                    textfield(controller.replicationsCount)
                }
                row {
                    label("Number of patients per replication:")
                    textfield(controller.numberOfPatients)
                }
                row {
                    label("Number of administrative workers:")
                    textfield(controller.numberOfAdminWorkers)
                }
                row {
                    label("Number of doctors:")
                    textfield(controller.numberOfDoctors)
                }
                row {
                    label("Number of nurses:")
                    textfield(controller.numberOfNurses)
                }
            }
            vbox(smallSpaces) {
                checkbox("Doctors experiment", controller.useDoctorsExperiment) {
                    setOnAction {
                        if (controller.useDoctorsExperiment.value) {
                            controller.withAnimation.value = false
                        }
                        withAnimationCheckBox.isDisable = controller.useDoctorsExperiment.value
                    }
                }
                gridpane {
                    vgap = smallSpaces.toDouble()
                    hgap = smallSpaces.toDouble()
                    row {
                        label("From doctors count:")
                        textfield(controller.fromDoctors)
                    }
                    row {
                        label("To doctors count:")
                        textfield(controller.toDoctors)
                    }
                    row {
                        label("Replications per experiment:")
                        textfield(controller.numReplicPerExperiment)
                    }
                }
                checkbox("Use early arrivals", controller.useEarlyArrivals)
//                button("Show Chart") {
//                    prefWidth = 150.0
//                    action { DoctorsExperimentChart().openWindow(owner = null) }
//                }
            }
            vbox(smallSpaces) {
                withAnimationCheckBox = checkbox("With animation", controller.withAnimation)
                hbox(smallSpaces) {
                    label("Delay every (sim. seconds): ")
                    label(controller.delayEveryStr)
                }
                slider(1..180) {
                    bind(controller.delayEvery)
                    prefWidth = preferredWidth
                    isShowTickLabels = true
                    majorTickUnit = 89.0
                }
                hbox(smallSpaces) {
                    label("Delay for (seconds): ")
                    label(controller.delayForStr)
                }
                slider(0.1..3.0) {
                    bind(controller.delayFor)
                    prefWidth = preferredWidth
                    isShowTickLabels = true
                    majorTickUnit = 1.4
                }
            }
            vbox(smallSpaces) {
                hbox(smallSpaces) {
                    label("Status: ")
                    label(controller.state)
                }
                button("Start/Pause") {
                    action { controller.startPause() }
                    prefWidth = preferredWidth
                }
                button("Stop") {
                    action { controller.stop() }
                    prefWidth = preferredWidth
                }
            }
        }
        separator()

        hbox(largeSpaces) {
            hbox(smallSpaces) {
                prefWidth = preferredWidth * 2 + largeSpaces
                addClass(Styles.smallPadding)
                label("Actual simulation time:") { addClass(Styles.smallHeading) }
                label(controller.actualSimTime)
            }
            hbox(smallSpaces) {
                prefWidth = preferredWidth * 2 + largeSpaces
                addClass(Styles.smallPadding)
                label("Simulation seconds:") { addClass(Styles.smallHeading) }
                label(controller.actualSimSeconds)
            }
            hbox(smallSpaces) {
                prefWidth = preferredWidth * 2 + largeSpaces
                addClass(Styles.smallPadding)
                label("Replication number:") { addClass(Styles.smallHeading) }
                label(controller.currentReplicNumber)
            }
        }

        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab(find<Room>(mapOf(Room::room to controller.registrationRoom)))
            tab(find<Room>(mapOf(Room::room to controller.examinationRoom)))
            tab(find<Room>(mapOf(Room::room to controller.vaccinationRoom)))
            tab(find<Room>(mapOf(Room::room to controller.registrationRoom)))
        }
    }

}