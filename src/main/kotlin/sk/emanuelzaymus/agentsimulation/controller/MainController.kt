package sk.emanuelzaymus.agentsimulation.controller

import OSPABA.Simulation
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation
import tornadofx.Controller
import tornadofx.onChange

class MainController : Controller() {

    private val initReplicationsCount = 1
    private val initNumberOfPatients = 540
    private val initNumberOfAdminWorkers = 5
    private val initNumberOfDoctors = 6
    private val initNumberOfNurses = 3
    private val initWithAnimation = true

    private val startTime = 8 * 60 * 60.0

    private val sim = VaccinationCentreAgentSimulation(
        initNumberOfPatients,
        initNumberOfAdminWorkers,
        initNumberOfDoctors,
        initNumberOfNurses,
        false
    )

    val replicationsCount = SimpleStringProperty(initReplicationsCount.toString())
    val numberOfPatients = SimpleStringProperty(initNumberOfPatients.toString())
    val numberOfAdminWorkers = SimpleStringProperty(initNumberOfAdminWorkers.toString())
    val numberOfDoctors = SimpleStringProperty(initNumberOfDoctors.toString())
    val numberOfNurses = SimpleStringProperty(initNumberOfNurses.toString())

    val useDoctorsExperiment = SimpleBooleanProperty(false)
    val fromDoctors = SimpleStringProperty("1")
    val toDoctors = SimpleStringProperty("20")
    val numReplicPerExperiment = SimpleStringProperty("1000")

    val withAnimation = SimpleBooleanProperty(initWithAnimation).apply { onChange { setSpeed() } }
    val delayEvery = SimpleDoubleProperty(60.0).apply {
        onChange {
            setSpeed()
            Platform.runLater { delayEveryStr.value = it.roundToString(1) }
        }
    }
    val delayEveryStr = SimpleStringProperty("60.0")
    val delayFor = SimpleDoubleProperty(1.0).apply {
        onChange {
            setSpeed()
            Platform.runLater { delayForStr.value = it.roundToString(1) }
        }
    }
    val delayForStr = SimpleStringProperty("1.0")

    val state = SimpleStringProperty("READY")
    val actualSimTime = SimpleStringProperty(startTime.secondsToTime())
    val actualSimSeconds = SimpleDoubleProperty(.0)
    val currentReplicNumber = SimpleIntegerProperty(1)

    private fun setSpeed() {
        if (withAnimation.value)
            sim.setSimSpeed(delayEvery.doubleValue(), delayFor.doubleValue())
        else
            sim.setMaxSimSpeed()
    }

    fun startPause() {
        if (!sim.isRunning)
            start()
        else if (!sim.isPaused)
            sim.pauseSimulation()
        else
            sim.resumeSimulation()
    }

    private fun start() {
        setSpeed()
        sim.simulateAsync(replicationsCount.get().toInt())
    }

    fun stop() = sim.stopSimulation()

    init {
        sim.onRefreshUI { refreshUI(it) }
    }

    private fun refreshUI(sim: Simulation) = Platform.runLater {
        actualSimTime.value = (sim.currentTime() + startTime).secondsToTime()
        actualSimSeconds.value = sim.currentTime()
        currentReplicNumber.value = sim.currentReplication() + 1
    }


}