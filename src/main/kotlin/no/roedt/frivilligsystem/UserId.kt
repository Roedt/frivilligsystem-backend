package no.roedt.frivilligsystem

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class UserId(val userId: Int)