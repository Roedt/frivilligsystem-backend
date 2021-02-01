package no.roedt.frivilligsystem

import no.roedt.frivilligsystem.registrer.RegistrerNyFrivilligRequest
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FrivilligService(
    val frivilligRepository: FrivilligRepository,
    val personRepository: PersonRepository
) {

    val frivillige = mutableListOf<Frivillig>()

    fun hentAlle(userId: UserId): List<Frivillig> {
        return frivillige
    }

    fun registrerNyFrivillig(request: RegistrerNyFrivilligRequest): Frivillig {
        val person = Person(
            navn = request.navn,
            epost = request.epost,
            telefonnummer = request.telefonnummer.nummer,
            postnummer = request.postnummer.getPostnummer(),
            hypersysID = null, //TODO,
            lokallag = getLokallagFraPostnummer(request.postnummer),
            rolle = Rolle.frivillig
        )
        val frivillig = Frivillig(
            alleredeAktivILokallag = request.alleredeAktivILokallag,
            medlemIRoedt = request.medlemIRoedt,
       //     kanTenkeSegAaBidraMedAktiviteter = request.kanTenkeSegAaBidraMedAktiviteter,
            spesiellKompetanse = request.spesiellKompetanse,
            andreTingDuVilBidraMed = request.andreTingDuVilBidraMed,
            fortellLittOmDegSelv = request.fortellLittOmDegSelv,
        )
        personRepository.persist(person)
        frivillig.person = person
        frivillige.add(frivillig)
        frivilligRepository.persist(frivillig)
        return frivillig
    }

    private fun getLokallagFraPostnummer(postnummer: Postnummer): Lokallag? {
        return Lokallag(name = "Bjerke")
        //TODO("Not yet implemented")
    }
}