package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPStat.Stat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity.IStatisticsAgent

class AgentStats {

    val queueLengthStat = Stat()
    val waitingTimeStat = Stat()
    val workersWorkload = Stat()

    fun clear() {
        queueLengthStat.clear()
        waitingTimeStat.clear()
        workersWorkload.clear()
    }

    fun addStatistics(agent: IStatisticsAgent<*>) {
        queueLengthStat.addSample(agent.averageQueueLength)
        waitingTimeStat.addSample(agent.averageWaitingTime)
        workersWorkload.addSample(agent.averageWorkload)
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