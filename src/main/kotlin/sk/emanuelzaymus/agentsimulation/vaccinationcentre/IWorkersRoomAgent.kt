package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreWorker

interface IWorkersRoomAgent<T : VaccinationCentreWorker> {
    fun <R> convertWorkers(transform: (T) -> R): Iterable<R>
}