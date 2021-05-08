package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.activity

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.IWorkersRoomAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

interface IStatisticsAgent<T : VaccinationCentreWorker> : IWorkersRoomAgent<T> {
    val actualQueueLength: Int
    val averageQueueLength: Double
    val averageWaitingTime: Double
    val busyWorkersCount: Int
    val averageWorkload: Double
}