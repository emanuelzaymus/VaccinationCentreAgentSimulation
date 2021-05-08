package sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction

interface ICountRoomAgent {
    val actualCount: Int
    val averageCount: Double
}