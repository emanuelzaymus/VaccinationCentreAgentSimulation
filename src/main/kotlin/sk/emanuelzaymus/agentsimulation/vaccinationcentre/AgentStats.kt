package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.IStatisticsAgent

class AgentStats {

    val queueLengthStat = Stat()
    val waitingTimeStat = Stat()
    val workersWorkload = Stat()

    fun clear() {
        queueLengthStat.clear()
        waitingTimeStat.clear()
        workersWorkload.clear()
    }

    fun addStatistics(agent: IStatisticsAgent) {
        queueLengthStat.addSample(agent.queueLengthMean())
        waitingTimeStat.addSample(agent.waitingTimeMean())
        workersWorkload.addSample(agent.workersWorkloadMean())
    }

    fun print(name: String) = println(
        """
        $name
        Queue length mean: ${queueLengthStat.mean()}
        Waiting time mean: ${waitingTimeStat.mean()}
        Workload mean: ${workersWorkload.mean()}
        """.trimIndent()
    )

}