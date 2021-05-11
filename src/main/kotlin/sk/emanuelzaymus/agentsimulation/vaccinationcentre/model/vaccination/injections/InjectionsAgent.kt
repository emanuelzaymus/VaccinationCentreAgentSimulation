package sk.emanuelzaymus.agentsimulation.vaccinationcentre.model.vaccination.injections

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.ICountRoomAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.Ids
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.MessageCodes
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstraction.VaccinationCentreAgent
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.INJECTIONS_COUNT_IN_PACKAGE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.constants.INJECTIONS_COUNT_TO_PREPARE
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.countLastStats

/**
 * Represents transfer of nurses to and from injections preparation room and the preparation itself.
 */
class InjectionsAgent(mySim: Simulation, parent: Agent, private val maxNumberOfPreparing: Int) :
    VaccinationCentreAgent(Ids.injectionsAgent, mySim, parent), ICountRoomAgent {

    val queue = SimQueue<InjectionsPreparationMessage>(WStat(mySim))
    private var preparingNurses = 0

    var vaccinesInPackageLeft: Int = INJECTIONS_COUNT_IN_PACKAGE
        private set

    init {
        InjectionsManager(mySim, this)
        ToInjectionsTransferProcess(mySim, this)
        InjectionsPreparationProcess(mySim, this)
        FromInjectionsTransferProcess(mySim, this)
        OpeningNewPackageProcess(mySim, this)

        addOwnMessage(MessageCodes.injectionsPreparationStart)
        addOwnMessage(MessageCodes.injectionsPreparationEnd)

        addOwnMessage(MessageCodes.transferEnd)
        addOwnMessage(MessageCodes.openingNewPackageEnd)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        preparingNurses = 0
        queue.clear()
    }

    override fun countLastStats() = queue.countLastStats()

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

    fun takeVaccines() {
        vaccinesInPackageLeft -= INJECTIONS_COUNT_TO_PREPARE
    }

    fun restartVaccinesInPackageLeft() {
        vaccinesInPackageLeft = INJECTIONS_COUNT_IN_PACKAGE
    }

    override val actualCount get() = preparingNurses

    override val averageCount get() = queue.lengthStatistic().mean()

    override fun checkFinalState() {
        if (preparingNurses != 0)
            throw IllegalStateException("InjectionsAgent - there are still nurses preparing injections.")
        if (queue.isNotEmpty())
            throw IllegalStateException("InjectionsAgent - there are still waiting nurses in the queue.")
    }

    fun printStats() = println(
        """
        Injections Preparation
        Queue length mean: $averageCount
        """.trimIndent()
    )

}