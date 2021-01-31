package no.roedt.frivilligsystem

import no.roedt.frivilligsystem.registrer.ErMedlemStatus
import no.roedt.frivilligsystem.registrer.RegistrerNyFrivilligRequest
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FrivilligService(val frivilligRepository: FrivilligRepository) {

    val frivillige = mutableListOf<Frivillig>()

    fun hentAlle(userId: UserId): List<Frivillig> {
        frivillige.add(Frivillig(
            navn = "Kari Nordmann",
            epost = "kari@nordmann.no",
            telefonnummer = Telefonnummer(nummer = 12345678).nummer,
            postnummer = Postnummer("0952").getPostnummer(),
            alleredeAktivILokallag = true,
            medlemIRoedt = ErMedlemStatus.Ja,
   //         kanTenkeSegAaBidraMedAktiviteter = listOf(),
            spesiellKompetanse = "Koding",
            andreTingDuVilBidraMed = null,
            fortellLittOmDegSelv = null,
            hypersysID = null,
            lokallag = Lokallag(name = "Grorud"),
            rolle = Rolle.frivillig
        ))
        frivillige.add(Frivillig(
            navn = "Ola Nordmann",
            epost = "ola@nordmann.no",
            telefonnummer = Telefonnummer(nummer = 87654321).nummer,
            postnummer = Postnummer("0952").getPostnummer(),
            alleredeAktivILokallag = false,
            medlemIRoedt = ErMedlemStatus.KanTenkeMegAaBliMedlem,
     //       kanTenkeSegAaBidraMedAktiviteter = listOf(Aktivitet.Bil, Aktivitet.DeleInnhold),
            spesiellKompetanse = null,
            andreTingDuVilBidraMed = null,
            fortellLittOmDegSelv = null,
            lokallag = Lokallag(name = "Grorud"),
            hypersysID = 1,
            rolle = Rolle.frivillig
        ))
        frivillige.add(Frivillig(
            navn = "Bengt Medelsvensson",
            epost = "bengt@medelsvensson.se",
            telefonnummer = Telefonnummer(landkode = "46", nummer = 234567890).nummer,
            postnummer = Postnummer("1122").getPostnummer(),
            alleredeAktivILokallag = true,
            medlemIRoedt = ErMedlemStatus.Nei,
//            kanTenkeSegAaBidraMedAktiviteter = listOf(Aktivitet.Ringe, Aktivitet.SMS),
            spesiellKompetanse = null,
            andreTingDuVilBidraMed = null,
            fortellLittOmDegSelv = null,
            lokallag = null,
            hypersysID = null,
            rolle = Rolle.frivillig
        ))
        return frivillige
    }

    fun registrerNyFrivillig(request: RegistrerNyFrivilligRequest): Frivillig {
        val frivillig = Frivillig(
            navn = request.navn,
            epost = request.epost,
            telefonnummer = request.telefonnummer.nummer,
            postnummer = request.postnummer.getPostnummer(),
            alleredeAktivILokallag = request.alleredeAktivILokallag,
            medlemIRoedt = request.medlemIRoedt,
       //     kanTenkeSegAaBidraMedAktiviteter = request.kanTenkeSegAaBidraMedAktiviteter,
            spesiellKompetanse = request.spesiellKompetanse,
            andreTingDuVilBidraMed = request.andreTingDuVilBidraMed,
            fortellLittOmDegSelv = request.fortellLittOmDegSelv,
            hypersysID = null, //TODO,
            lokallag = getLokallagFraPostnummer(request.postnummer),
            rolle = Rolle.frivillig
        )
        frivillige.add(frivillig)
        frivilligRepository.persist(frivillig)
        return frivillig
    }

    private fun getLokallagFraPostnummer(postnummer: Postnummer): Lokallag? {
        return Lokallag(name = "Bjerke")
        //TODO("Not yet implemented")
    }
}