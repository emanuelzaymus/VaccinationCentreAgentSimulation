package sk.emanuelzaymus.agentsimulation.vaccinationcentre

import OSPDataStruct.SimQueue

fun SimQueue<*>.countLastStats() = lengthStatistic().addSample(size.toDouble())
