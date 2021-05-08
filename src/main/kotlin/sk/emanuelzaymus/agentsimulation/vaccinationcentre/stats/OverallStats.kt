package sk.emanuelzaymus.agentsimulation.vaccinationcentre.stats

import OSPStat.Stat

class OverallStats {

    val overallWaiting = Stat()
    val overallWorkload = Stat()

    fun addWaiting(avgRegWaiting: Double, avgExamWaiting: Double, avgVacWaiting: Double) =
        overallWaiting.addSample(avgRegWaiting + avgExamWaiting + avgVacWaiting)

    fun addWorkload(
        avgAdminWorkload: Double, adminCount: Int,
        avgDoctorWorkload: Double, doctorCount: Int,
        avgNurseWorkload: Double, nurseCount: Int
    ) {
        val allWorkersWorkload = avgAdminWorkload * adminCount + avgDoctorWorkload * doctorCount +
                avgNurseWorkload * nurseCount
        val workersCount = adminCount + doctorCount + nurseCount

        overallWorkload.addSample(allWorkersWorkload / workersCount)
    }

    fun clear() {
        overallWaiting.clear()
        overallWorkload.clear()
    }

}