package sk.emanuelzaymus.agentsimulation.vaccinationcentre.experiments

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/**
 * Experiment for multiple simulation run for different number of doctors.
 */
class DoctorsExperiment(
    private val numberOfPatients: Int,
    private val numberOfAdminWorkers: Int,
    private val fromDoctors: Int,
    private val toDoctors: Int,
    private val numberOfNurses: Int,
    private val earlyArrivals: Boolean,
    private val zeroTransitions: Boolean
) : VaccinationCentreExperiment() {

    private val isStopped = AtomicBoolean(false)

    override fun simulateAsync(replicationCount: Int) {
        onBeforeExperimentFun?.invoke()
        thread(name = "DOCTORS EXPERIMENT THREAD", isDaemon = true, priority = 10) {

            for (doctors in fromDoctors..toDoctors) {
                sim = VaccinationCentreAgentSimulation(
                    numberOfPatients, numberOfAdminWorkers, doctors, numberOfNurses, earlyArrivals, zeroTransitions
                )

                applyDelegates()
                sim.simulate(replicationCount)

                if (isStopped.get())
                    break
            }
        }
    }

    override fun stopExperiment() {
        super.stopExperiment()
        isStopped.set(true)
    }

}