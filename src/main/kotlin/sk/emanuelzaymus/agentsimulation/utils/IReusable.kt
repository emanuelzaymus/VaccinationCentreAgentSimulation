package sk.emanuelzaymus.agentsimulation.utils

interface IReusable : IRestartable {
    fun checkFinalState()
}