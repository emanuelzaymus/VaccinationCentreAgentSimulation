package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

interface IWorkersRoomAgent<T : VaccinationCentreWorker> {
    fun <R> convertWorkers(transform: (T) -> R): Iterable<R>
}