package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

interface IStatisticsAgent {
    
    fun queueLengthMean(): Double

    fun waitingTimeMean(): Double

    fun workersWorkloadMean(): Double

}