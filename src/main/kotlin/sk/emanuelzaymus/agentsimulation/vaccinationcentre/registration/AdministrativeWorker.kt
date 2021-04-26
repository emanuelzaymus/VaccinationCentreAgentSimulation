package sk.emanuelzaymus.agentsimulation.vaccinationcentre.registration

import OSPStat.WStat
import sk.emanuelzaymus.agentsimulation.vaccinationcentre.abstract.VaccinationCentreWorker

class AdministrativeWorker(workloadStat: WStat) : VaccinationCentreWorker(workloadStat)