package no.roedt.frivilligsystem.token

import no.roedt.frivilligsystem.hypersys.LoginRequest
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/token")
@Tag(name ="Token")
@SecurityRequirement(name = "jwt")
class TokenController(val tokenGenerator: TokenGenerator) {

    @Inject
    lateinit var jwt: JsonWebToken

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    fun login(loginRequest: LoginRequest): String = tokenGenerator.login(loginRequest)

    @RolesAllowed("ringar")
    @POST
    @Path("/refresh")
    @Produces(MediaType.TEXT_PLAIN)
    fun refresh(@Context ctx: SecurityContext): String = tokenGenerator.refresh(jwt)

}