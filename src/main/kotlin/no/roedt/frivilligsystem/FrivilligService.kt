package no.roedt.frivilligsystem

import no.roedt.frivilligsystem.kontakt.AutentisertRegistrerKontaktRequest
import no.roedt.frivilligsystem.kontakt.Kontakt
import no.roedt.frivilligsystem.kontakt.KontaktRepository
import no.roedt.frivilligsystem.registrer.RegistrerNyFrivilligRequest
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FrivilligService(
    val frivilligRepository: FrivilligRepository,
    val personRepository: PersonRepository,
    val kontaktRepository: KontaktRepository
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

    fun registrerKontakt(request: AutentisertRegistrerKontaktRequest) =
        kontaktRepository.persist(
            Kontakt(
            frivillig_id = request.request.frivillig_id,
            tilbakemelding = request.request.tilbakemelding,
            registrert_av = personRepository.find("hypersysID", request.userId.userId).firstResult<Person>().id
        )
        )
}