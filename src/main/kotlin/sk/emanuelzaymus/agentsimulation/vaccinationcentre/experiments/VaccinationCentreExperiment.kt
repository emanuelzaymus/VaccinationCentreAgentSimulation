package sk.emanuelzaymus.agentsimulation.vaccinationcentre.experiments

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation

open class VaccinationCentreExperiment(
    numberOfPatients: Int,
    numberOfAdminWorkers: Int,
    numberOfDoctors: Int,
    numberOfNurses: Int,
    earlyArrivals: Boolean,
    zeroTransitions: Boolean
) {

    var sim = VaccinationCentreAgentSimulation(
        numberOfPatients, numberOfAdminWorkers, numberOfDoctors, numberOfNurses, earlyArrivals, zeroTransitions
    )
        protected set

//    val isRunning: Boolean get() = sim.isRunning
//    val isPaused: Boolean get() = sim.isPaused
//
//    fun setSimSpeed(interval: Double, duration: Double) = sim.setSimSpeed(interval, duration)
//
//    fun setMaxSimSpeed() = sim.setMaxSimSpeed()
//
//    fun pauseSimulation() = sim.pauseSimulation()
//
//    fun resumeSimulation() = sim.resumeSimulation()
//
//    fun simulateAsync(replicationCount: Int) = sim.simulateAsync(replicationCount)
//
//    fun stopSimulation() = sim.stopSimulation()

}