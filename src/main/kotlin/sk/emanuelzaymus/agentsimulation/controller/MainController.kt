package sk.emanuelzaymus.agentsimulation.controller

import OSPABA.ISimDelegate
import OSPABA.SimState
import OSPABA.Simulation
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import sk.emanuelzaymus.agentsimulation.controller.roomdata.*
import sk.emanuelzaymus.agentsimulation.controller.workerdata.NurseData
import sk.emanuelzaymus.agentsimulation.controller.workerdata.WorkerData
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.DEBUG_MODE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation
import tornadofx.Controller
import tornadofx.alert
import tornadofx.onChange

class MainController : Controller(), ISimDelegate {

    private val initReplicationsCount = 1
    private val initNumberOfPatients = 540
    private val initNumberOfAdminWorkers = 5
    private val initNumberOfDoctors = 6
    private val initNumberOfNurses = 3
    private val initWithAnimation = true

    private val startTime = 8 * 60 * 60.0

    private var sim = VaccinationCentreAgentSimulation(
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

    val useEarlyArrivals = SimpleBooleanProperty(false)

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

    val state = SimpleStringProperty("-")
    val actualSimTime = SimpleStringProperty(startTime.secondsToTime())
    val actualSimSeconds = SimpleDoubleProperty(.0)
    val currentReplicNumber = SimpleIntegerProperty(1)

    val registrationRoom = RoomData("Registration", "Administrative Workers") { WorkerData.create(it) }
    val examinationRoom = RoomData("Examination", "Doctors") { WorkerData.create(it) }
    val vaccinationRoom = RoomData("Vaccination", "Nurses", true) { NurseData.create(it) }
    val injectionsPrepRoomData = CountRoomData("Injections Preparation Room", "nurses")
    val waitingRoomData = CountRoomData("Waiting Room", "patients")
    val lunchBreakData = LunchBreakData()
    val overallData = OverallData()

    private fun setSpeed() {
        DEBUG_MODE = withAnimation.value
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
        if (restart()) {
            sim.simulateAsync(replicationsCount.get().toInt())
        }
    }

    fun stop() = sim.stopSimulation()

    override fun simStateChanged(sim: Simulation, simState: SimState) = Platform.runLater {
        state.value = simState.name
    }

    override fun refresh(sim: Simulation) = refreshUI(sim)

    private fun refreshUI(sim: Simulation) = Platform.runLater {
        actualSimTime.value = (sim.currentTime() + startTime).secondsToTime()
        actualSimSeconds.value = sim.currentTime()

        sim as VaccinationCentreAgentSimulation
        registrationRoom.refresh(sim.registrationAgent, sim.registrationStats, sim.transferAgent.examinationCount)
        examinationRoom.refresh(sim.examinationAgent, sim.examinationStats, sim.transferAgent.vaccinationCount)
        vaccinationRoom.refresh(sim.vaccinationAgent, sim.vaccinationStats, sim.transferAgent.waitingCount)
        injectionsPrepRoomData.refresh(sim.injectionsAgent, sim.nursesQueueLengthStats)
        waitingRoomData.refresh(sim.waitingAgent, sim.waitingStats)
        lunchBreakData.refresh(sim.lunchBreakAgent)
        overallData.refresh(sim.overallStats.overallWaiting, sim.overallStats.overallWorkload)
    }

    private fun refreshCurrentReplic(sim: Simulation) =
        Platform.runLater { currentReplicNumber.value = sim.currentReplication() + 1 }

    private fun restart(): Boolean {
        try {
            replicationsCount.value.toInt()
            val patients = numberOfPatients.value.toInt()
            val workers = numberOfAdminWorkers.value.toInt()
            val doctors = numberOfDoctors.value.toInt()
            val nurses = numberOfNurses.value.toInt()
            val earlyArrivals: Boolean = useEarlyArrivals.value

            sim = VaccinationCentreAgentSimulation(patients, workers, doctors, nurses, earlyArrivals).also {
                it.onPause { sim -> refreshUI(sim) }
                it.onReplicationWillStart { sim -> setSpeed(); refreshCurrentReplic(sim) }
                it.onSimulationDidFinish { sim -> refreshUI(sim) }

                it.registerDelegate(this)
            }

            return true
        } catch (e: NumberFormatException) {
            showInvalidInputsAlert()
        }
        return false
    }

    private fun showInvalidInputsAlert() = alert(
        Alert.AlertType.ERROR,
        "Invalid Inputs",
        "Make sure you put valid inputs, please.",
        title = "Attention"
    )

}
