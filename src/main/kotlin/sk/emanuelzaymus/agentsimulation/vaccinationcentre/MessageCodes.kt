package sk.emanuelzaymus.agentsimulation.vaccinationcentre

class MessageCodes {

    companion object {
        const val init = 7

        const val vehicleArrival = 1
        const val vehicleLeaving = 2
        const val vehicleService = 3
        const val vehicleServiceDone = 4

        const val serviceEnd = 5
        const val newVehicle = 6
    }

}