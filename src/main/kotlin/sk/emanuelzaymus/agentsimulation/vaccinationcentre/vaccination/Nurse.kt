package sk.emanuelzaymus.agentsimulation.vaccinationcentre.vaccination

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker

class Nurse(workloadStat: WStat) : VaccinationCentreWorker(workloadStat)