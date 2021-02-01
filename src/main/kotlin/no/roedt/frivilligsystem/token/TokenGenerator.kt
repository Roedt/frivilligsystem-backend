package no.roedt.frivilligsystem.token

import io.smallrye.jwt.build.Jwt
import no.roedt.frivilligsystem.Person
import no.roedt.frivilligsystem.PersonRepository
import no.roedt.frivilligsystem.Rolle
import no.roedt.frivilligsystem.hypersys.*
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import java.time.Duration
import javax.enterprise.context.RequestScoped
import javax.json.JsonNumber
import kotlin.math.max


@RequestScoped
class TokenGenerator(
    private val hypersysService: HypersysService,
    private val personRepository: PersonRepository,
    private val privateKeyFactory: PrivateKeyFactory
) {

    @ConfigProperty(name = "frontendTokenKey")
    lateinit var frontendTokenKey: String

    fun login(loginRequest: LoginRequest): String {
        if (loginRequest.key != frontendTokenKey) {
            throw IllegalArgumentException("Illegal key")
        }

        val hypersysToken: Token = hypersysService.login(loginRequest)
        if (hypersysToken is UgyldigToken)
            throw RuntimeException(hypersysToken.error)
        return generateToken(hypersysToken as GyldigPersonToken)
    }

    private fun generateToken(hypersysToken: GyldigPersonToken): String = Jwt
        .audience("frivillig")
        .issuer("https://roedt.no")
        .subject("Frivillgsystem")
        .upn("Frivilligsystem")
        .issuedAt(System.currentTimeMillis())
        .expiresAt(System.currentTimeMillis() + Duration.ofHours(1).toSeconds())
        .groups(getGroups(hypersysToken))
        .claim("hypersys.token_type", hypersysToken.token_type)
        .claim("hypersys.scope", hypersysToken.scope)
        .claim("hypersys.access_token", hypersysToken.access_token)
        .claim("hypersys.expires_in", hypersysToken.expires_in)
        .claim("hypersys.refresh_token", hypersysToken.refresh_token)
        .claim("hypersys.user_id", hypersysToken.user_id)
        .sign(privateKeyFactory.readPrivateKey())

    private fun getGroups(hypersysToken: GyldigPersonToken): Set<String> =
        getPersonFromHypersysID(hypersysToken)
            .map { it.rolle }
            .map{ rolle ->
                when (rolle) {
                    Rolle.sentralt -> setOf(Rolle.lokallag.name, Rolle.distrikt.name, Rolle.sentralt.name)
                    Rolle.distrikt -> setOf(Rolle.lokallag.name, Rolle.distrikt.name)
                    Rolle.lokallag -> setOf(Rolle.lokallag.name)
                    Rolle.frivillig -> setOf(Rolle.frivillig.name)
                }
            }
            .orElse(setOf())

    private fun getPersonFromHypersysID(hypersysToken: GyldigPersonToken) =
        personRepository.find("hypersysID", hypersysToken.user_id.toInt()).firstResultOptional<Person>()

    fun refresh(jwt: JsonWebToken): String = generateToken(
        GyldigPersonToken(
            token_type = extract(jwt, "token_type"),
            scope = extract(jwt, "scope"),
            access_token = extract(jwt, "access_token"),
            expires_in = getExpiresIn(jwt),
            refresh_token = extract(jwt, "refresh_token"),
            user_id = extract(jwt, "user_id")
        )
    )

    private fun getExpiresIn(jwt: JsonWebToken): Int {
        val fromHypersys = jwt.claim<JsonNumber>("hypersys.expires_in").get().longValue()
        val sinceIssued = (System.currentTimeMillis() - jwt.issuedAtTime) / 1000
        return max(fromHypersys - sinceIssued, 0).toInt()
    }

    private fun extract(jwt: JsonWebToken, claim: String) = jwt.claim<String>("hypersys.$claim").get()
}
