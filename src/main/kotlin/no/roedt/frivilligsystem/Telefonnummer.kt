package no.roedt.frivilligsystem

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Telefonnummer(
        val landkode: String = "+47",
        val nummer: Int
)