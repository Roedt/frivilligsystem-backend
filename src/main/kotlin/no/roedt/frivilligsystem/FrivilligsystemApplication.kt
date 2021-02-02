package no.roedt.frivilligsystem

import org.eclipse.microprofile.auth.LoginConfig
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.info.License
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme
import javax.annotation.security.DeclareRoles
import javax.ws.rs.core.Application

@OpenAPIDefinition(
    info = Info(
        title="Frivilligsystem-API",
        version = "1.0.0",
        contact = Contact(
            name = "Mads Opheim",
            url = "http://github.com/roedt/frivilligsystem-backend"),
        license = License(
            name = "MIT License",
            url = "https://github.com/Roedt/frivilligsystem-backend/blob/main/LICENSE"),
        description = "Takk til Erik Bolstad for postnummer. http://www.erikbolstad.no/geo/noreg/postnummer/"
    )
)
@SecurityScheme(
    securitySchemeName = "jwt",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "jwt"
)
@LoginConfig(authMethod = "MP-JWT", realmName = "jwt-jaspi")
@DeclareRoles("ringar")
class FrivilligsystemApplication : Application()