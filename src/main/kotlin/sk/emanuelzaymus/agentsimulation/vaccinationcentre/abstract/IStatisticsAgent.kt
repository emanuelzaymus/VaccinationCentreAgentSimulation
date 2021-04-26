package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract

interface IStatisticsAgent {
    
    fun queueLengthMean(): Double

    fun waitingTimeMean(): Double

    fun workersWorkloadMean(): Double

}