package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.CountRoomAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class InjectionsAgent(mySim: Simulation, parent: Agent, private val maxNumberOfPreparing: Int) :
    VaccinationCentreAgent(Ids.injectionsAgent, mySim, parent), CountRoomAgent {

    val queue = SimQueue<InjectionsPreparationMessage>(WStat(mySim))
    private var preparingNurses = 0

    init {
        InjectionsManager(mySim, this)
        ToInjectionsTransferProcess(mySim, this)
        InjectionsPreparationProcess(mySim, this)
        FromInjectionsTransferProcess(mySim, this)

        addOwnMessage(MessageCodes.injectionsPreparationStart)
        addOwnMessage(MessageCodes.injectionsPreparationEnd)

        addOwnMessage(MessageCodes.transferEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        preparingNurses = 0
        queue.clear()
    }

    fun canStartAnotherPreparation(): Boolean = preparingNurses < maxNumberOfPreparing

    fun startPreparation() {
        if (preparingNurses >= maxNumberOfPreparing) {
            throw IllegalStateException("Cannot increment preparingNurses - is already $maxNumberOfPreparing")
        }
        preparingNurses++
    }

    fun preparationDone() {
        if (preparingNurses <= 0) {
            throw IllegalStateException("Cannot decrement preparingNurses - is already 0")
        }
        preparingNurses--
    }

    fun queueLengthMean(): Double = queue.lengthStatistic().mean()

    override fun getCount() = preparingNurses

    override fun getAverage() = queue.lengthStatistic().mean()

    override fun checkFinalState() {
        if (preparingNurses != 0)
            throw IllegalStateException("InjectionsAgent - there are still nurses preparing injections.")
        if (queue.isNotEmpty())
            throw IllegalStateException("InjectionsAgent - there are still waiting nurses in the queue.")
    }

    fun printStats() = println(
        """
        Injections Preparation
        Queue length mean: ${queueLengthMean()}
        """.trimIndent()
    )

}