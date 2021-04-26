package sk.emanuelzaymus.agentsimulation.vaccinationcentre.examination

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker

class Doctor(workloadStat: WStat) : VaccinationCentreWorker(workloadStat)