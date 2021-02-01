package no.roedt.frivilligsystem

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class AutentisertRegistrerKontaktRequest(
    val userId: UserId,
    val request: RegistrerKontaktRequest
)