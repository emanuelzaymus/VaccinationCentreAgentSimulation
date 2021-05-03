package sk.emanuelzaymus.agentsimulation.view

import javafx.scene.control.CheckBox
import sk.emanuelzaymus.agentsimulation.app.Styles
import sk.emanuelzaymus.agentsimulation.controller.MainController
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    private val smallSpaces = 10
    private val largeSpaces = 30
    private val preferredWidth = 180.0

    private val mainController: MainController by inject()

    private lateinit var withAnimationCheckBox: CheckBox

    override val root = vbox {
        hbox(largeSpaces) {
            addClass(Styles.biggerPadding)
            gridpane {
                vgap = smallSpaces.toDouble()
                hgap = smallSpaces.toDouble()
                row {
                    label("Replications count:")
                    textfield(mainController.replicationsCount)
                }
                row {
                    label("Number of patients per replication:")
                    textfield(mainController.numberOfPatients)
                }
                row {
                    label("Number of administrative workers:")
                    textfield(mainController.numberOfAdminWorkers)
                }
                row {
                    label("Number of doctors:")
                    textfield(mainController.numberOfDoctors)
                }
                row {
                    label("Number of nurses:")
                    textfield(mainController.numberOfNurses)
                }
            }
            vbox(smallSpaces) {
                checkbox("Doctors Experiment", mainController.useDoctorsExperiment) {
                    setOnAction {
                        if (mainController.useDoctorsExperiment.value) {
                            mainController.withAnimation.value = false
                        }
                        withAnimationCheckBox.isDisable = mainController.useDoctorsExperiment.value
                    }
                }
                gridpane {
                    vgap = smallSpaces.toDouble()
                    hgap = smallSpaces.toDouble()
                    row {
                        label("From Doctors Count:")
                        textfield(mainController.fromDoctors)
                    }
                    row {
                        label("To Doctors count:")
                        textfield(mainController.toDoctors)
                    }
                    row {
                        label("Replications per Experiment:")
                        textfield(mainController.numReplicPerExperiment)
                    }
                }
//                button("Show Chart") {
//                    prefWidth = 150.0
//                    action { DoctorsExperimentChart().openWindow(owner = null) }
//                }
            }
            vbox(smallSpaces) {
                withAnimationCheckBox = checkbox("With animation", mainController.withAnimation)
                hbox(smallSpaces) {
                    label("Delay every (sim. seconds): ")
                    label(mainController.delayEveryStr)
                }
                slider(1..180) {
                    bind(mainController.delayEvery)
                    prefWidth = preferredWidth
                    isShowTickLabels = true
                    majorTickUnit = 89.0
                }
                hbox(smallSpaces) {
                    label("Delay for (seconds): ")
                    label(mainController.delayForStr)
                }
                slider(0.1..3.0) {
                    bind(mainController.delayFor)
                    prefWidth = preferredWidth
                    isShowTickLabels = true
                    majorTickUnit = 1.4
                }
            }
            vbox(smallSpaces) {
                hbox(smallSpaces) {
                    label("Status: ")
                    label(mainController.state)
                }
                button("Start/Pause") {
                    action { mainController.startPause() }
                    prefWidth = preferredWidth
                }
                button("Stop") {
                    action { mainController.stop() }
                    prefWidth = preferredWidth
                }
            }
        }
        separator()
        hbox(largeSpaces) {
            hbox(smallSpaces) {
                prefWidth = preferredWidth * 2 + largeSpaces
                addClass(Styles.smallPadding)
                label("Actual Simulation Time:") { addClass(Styles.smallHeading) }
                label(mainController.actualSimTime)
            }
            hbox(smallSpaces) {
                prefWidth = preferredWidth * 2 + largeSpaces
                addClass(Styles.smallPadding)
                label("Simulation seconds:") { addClass(Styles.smallHeading) }
                label(mainController.actualSimSeconds)
            }
            hbox(smallSpaces) {
                prefWidth = preferredWidth * 2 + largeSpaces
                addClass(Styles.smallPadding)
                label("Replication number:") { addClass(Styles.smallHeading) }
                label(mainController.currentReplicNumber)
            }
        }
    }

}