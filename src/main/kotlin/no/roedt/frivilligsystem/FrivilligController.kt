package no.roedt.frivilligsystem

import no.roedt.frivilligsystem.registrer.RegistrerNyFrivilligRequest
import org.eclipse.microprofile.faulttolerance.Retry
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.annotation.security.PermitAll
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext


@Path("/")
@Tag(name = "Frivilligsystem")
@SecurityRequirement(name = "jwt")
class FrivilligController(val frivilligService: FrivilligService) {

    @Inject
    lateinit var jwt: JsonWebToken

    @RolesAllowed("lokallag", "distrikt", "sentralt")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alle")
    @Operation(summary = "Finn alle frivillige i laget")
    @Retry
    fun hentAlle(@Context ctx: SecurityContext): List<Frivillig> = frivilligService.hentAlle(ctx.userId())

    @PermitAll
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/registrer")
    @Operation(summary = "Registrer ny frivillig")
    @Retry
    fun registrerNyFrivillig(@Context ctx: SecurityContext, registrerNyFrivilligRequest: RegistrerNyFrivilligRequest): Frivillig = frivilligService.registrerNyFrivillig(registrerNyFrivilligRequest)

    fun SecurityContext.userId(): UserId = UserId((userPrincipal as JsonWebToken).claim<Any>("hypersys.user_id").get().toString().toInt())
}