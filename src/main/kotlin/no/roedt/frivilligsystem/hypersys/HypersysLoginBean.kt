package no.roedt.frivilligsystem.hypersys

import no.roedt.frivilligsystem.*
import no.roedt.frivilligsystem.hypersys.externalModel.Profile
import no.roedt.frivilligsystem.token.GCPSecretManager
import javax.enterprise.context.Dependent

@Dependent
class HypersysLoginBean(
    private val hypersysProxy: HypersysProxy,
    private val databaseUpdater: DatabaseUpdater,
    private val modelConverter: ModelConverter,
    private val gcpSecretManager: GCPSecretManager,
    private val personRepository: PersonRepository
) {

    fun login(loginRequest: LoginRequest): Token {
        val brukarId = gcpSecretManager.getHypersysBrukerId()
        val brukarSecret = gcpSecretManager.getHypersysBrukerSecret()
        val response = hypersysProxy.post(brukarId, brukarSecret, "grant_type=password&username=${loginRequest.brukarnamn}&password=${loginRequest.passord}")
        if (response.statusCode() != 200) {
            return hypersysProxy.readResponse(response, UgyldigToken::class.java)
        }

        val gyldigToken = hypersysProxy.readResponse(response, GyldigPersonToken::class.java)
        oppdaterRingerFraaHypersys(gyldigToken)
        return gyldigToken
    }

    private fun oppdaterRingerFraaHypersys(token: GyldigPersonToken) {
        val profile: Profile = hypersysProxy.get("actor/api/profile/", token, Profile::class.java)
        val brukarinformasjon: Brukarinformasjon = modelConverter.convert(profile)

        val person = brukarinformasjon.toPerson()
        if (personRepository.find("hypersysID", person.hypersysID).count() > 0L) {
            personRepository.update(
                "navn = '${person.navn}', " +
                        "epost = '${person.epost}'," +
                        "telefonnummer = ${person.telefonnummer}," +
                        "postnummer = ${person.postnummer}," +
                        "rolle = ${person.rolle}," +
                        "lokallag = ${person.lokallag} " +
                        "where hypersysID = ${person.hypersysID}"
            )
        }
        else {
            personRepository.persist(person)
        }
    }

    private fun Brukarinformasjon.toPerson() : Person = Person(
        hypersysID = hypersysID,
        navn = "$fornamn $etternamn",
        epost = epost,
        telefonnummer = telefonnummer.nummer,
        postnummer = postnummer.getPostnummer(),
        rolle = Rolle.lokallag, //TODO
        lokallag = null
    )
}