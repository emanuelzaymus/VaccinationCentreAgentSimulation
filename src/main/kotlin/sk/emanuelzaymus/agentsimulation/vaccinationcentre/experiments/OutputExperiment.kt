package sk.emanuelzaymus.agentsimulation.vaccinationcentre.experiments

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.VaccinationCentreAgentSimulation

object OutputExperiment {

    fun print() {
        for (admins in 12..14) {
            for (doctors in 18..20) {
                for (nurses in 5..7) {

                    val sim = VaccinationCentreAgentSimulation(
                        1700, admins, doctors, nurses, earlyArrivals = false, zeroTransitions = false
                    )
                    sim.simulate(200)

                    val regLen = sim.registrationStats.queueLengthStat.mean()
                    val regWait = sim.registrationStats.waitingTimeStat.mean()
                    val adm = sim.registrationStats.workersWorkload.mean()

                    val exaLen = sim.examinationStats.queueLengthStat.mean()
                    val exaWait = sim.examinationStats.waitingTimeStat.mean()
                    val doc = sim.examinationStats.workersWorkload.mean()

                    val vacLen = sim.vaccinationStats.queueLengthStat.mean()
                    val vacWait = sim.vaccinationStats.waitingTimeStat.mean()
                    val nur = sim.vaccinationStats.workersWorkload.mean()

                    println("$admins\t$doctors\t$nurses\t$regLen\t$regWait\t$exaLen\t$exaWait\t$vacLen\t$vacWait\t$adm\t$doc\t$nur")
                }
            }
        }
    }

}