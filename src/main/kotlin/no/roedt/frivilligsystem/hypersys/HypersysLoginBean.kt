package no.roedt.frivilligsystem.hypersys

import no.roedt.frivilligsystem.Brukarinformasjon
import no.roedt.frivilligsystem.DatabaseUpdater
import no.roedt.frivilligsystem.hypersys.externalModel.Profile
import no.roedt.frivilligsystem.token.GCPSecretManager
import javax.enterprise.context.Dependent

@Dependent
class HypersysLoginBean(
    private val hypersysProxy: HypersysProxy,
    private val databaseUpdater: DatabaseUpdater,
    private val modelConverter: ModelConverter,
    private val gcpSecretManager: GCPSecretManager
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

       //TODO: databaseUpdater.update(brukarinformasjon.toSQL())
    }

    private fun Brukarinformasjon.toSQL(): String = "CALL sp_registrerNyBruker(" +
            "'${hypersysID}', " +
            "'${fornamn}', " +
            "'${etternamn}', " +
            "'${telefonnummer.nummer}', " +
            "'${epost}', " +
            "${postnummer.postnummer}, " +
            "${fylke.nr}" +
            ")"
}