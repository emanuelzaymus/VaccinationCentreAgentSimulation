package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination.injections

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent

class InjectionsAgent(mySim: Simulation, parent: Agent, private val maxNumberOfPreparing: Int) :
    VaccinationCentreAgent(Ids.injectionsAgent, mySim, parent) {

    val queue = SimQueue<InjectionsPreparationMessage>(WStat(mySim))

    var preparingNurses = 0
        private set

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