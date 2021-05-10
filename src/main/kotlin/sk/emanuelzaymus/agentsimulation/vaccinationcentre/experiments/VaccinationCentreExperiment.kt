package sk.emanuelzaymus.agentsimulation.vaccinationcentre.experiments

import OSPABA.ISimDelegate
import OSPABA.Simulation
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation

open class VaccinationCentreExperiment {

    private lateinit var onPauseFun: (Simulation) -> Unit
    private lateinit var onReplicationWillStartFun: (Simulation) -> Unit
    private lateinit var onSimulationDidFinishFun: (Simulation) -> Unit
    private lateinit var delegateFun: ISimDelegate

    lateinit var sim: VaccinationCentreAgentSimulation
        protected set

    protected constructor()

    constructor(
        numberOfPatients: Int, numberOfAdminWorkers: Int, numberOfDoctors: Int,
        numberOfNurses: Int, earlyArrivals: Boolean, zeroTransitions: Boolean
    ) {
        sim = VaccinationCentreAgentSimulation(
            numberOfPatients, numberOfAdminWorkers, numberOfDoctors, numberOfNurses, earlyArrivals, zeroTransitions
        )
    }

    fun onPause(onPause: (Simulation) -> Unit) {
        onPauseFun = onPause
    }

    fun onReplicationWillStart(onReplicationWillStart: (Simulation) -> Unit) {
        onReplicationWillStartFun = onReplicationWillStart
    }

    fun onSimulationDidFinish(onSimulationDidFinish: (Simulation) -> Unit) {
        onSimulationDidFinishFun = onSimulationDidFinish
    }

    fun registerDelegate(delegate: ISimDelegate) {
        delegateFun = delegate
    }

    open fun simulateAsync(replicationCount: Int) {
        applyDelegates()
        sim.simulateAsync(replicationCount)
    }

    open fun stopExperiment() = sim.stopSimulation()

    protected fun applyDelegates() {
        sim.onPause(onPauseFun)
        sim.onReplicationWillStart(onReplicationWillStartFun)
        sim.onSimulationDidFinish(onSimulationDidFinishFun)
        sim.registerDelegate(delegateFun)
    }

}