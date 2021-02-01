package no.roedt.frivilligsystem

import no.roedt.frivilligsystem.registrer.RegistrerNyFrivilligRequest
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FrivilligService(
    val frivilligRepository: FrivilligRepository,
    val personRepository: PersonRepository
) {
    fun hentAlle(userId: UserId): List<Frivillig> = frivilligRepository.findAll().list()

    fun registrerNyFrivillig(request: RegistrerNyFrivilligRequest): Frivillig {
        val person = request.toPerson()
        personRepository.save(person)
        val frivillig = Frivillig(
            alleredeAktivILokallag = request.alleredeAktivILokallag,
            medlemIRoedt = request.medlemIRoedt,
            //     kanTenkeSegAaBidraMedAktiviteter = request.kanTenkeSegAaBidraMedAktiviteter,
            spesiellKompetanse = request.spesiellKompetanse,
            andreTingDuVilBidraMed = request.andreTingDuVilBidraMed,
            fortellLittOmDegSelv = request.fortellLittOmDegSelv,
        )
            .apply {
                this.person = personRepository.find("epost", person.epost).firstResult()
            }
        frivilligRepository.persist(frivillig)
        return frivillig
    }

    private fun RegistrerNyFrivilligRequest.toPerson() = Person(
        navn = navn,
        epost = epost,
        telefonnummer = telefonnummer.nummer,
        postnummer = postnummer.getPostnummer(),
        hypersysID = null, //TODO,
        lokallag = getLokallagFraPostnummer(postnummer),
        rolle = Rolle.frivillig
    )

    private fun getLokallagFraPostnummer(postnummer: Postnummer): Lokallag? {
        return null //Lokallag(name = "Bjerke")
        //TODO("Not yet implemented")
    }
}