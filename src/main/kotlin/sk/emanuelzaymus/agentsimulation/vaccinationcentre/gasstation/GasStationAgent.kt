package sk.emanuelzaymus.agentsimulation.vaccinationcentre.gasstation

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes

class GasStationAgent(id: Int = Ids.gasStationAgent, mySim: Simulation, parent: Agent) : Agent(id, mySim, parent) {

    lateinit var vehicleQueue: SimQueue<MessageForm>
    lateinit var waitingTimeStat: Stat
    var isWorking = false

    init {
        GasStationManager(mySim = mySim, myAgent = this)
        VehicleServiceProcess(mySim = mySim, myAgent = this)

        addOwnMessage(MessageCodes.vehicleService)
        addOwnMessage(MessageCodes.serviceEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        vehicleQueue = SimQueue<MessageForm>(WStat(mySim()))
        waitingTimeStat = Stat()
        isWorking = false
    }

    fun queueLengthStat(): WStat = vehicleQueue.lengthStatistic()

}