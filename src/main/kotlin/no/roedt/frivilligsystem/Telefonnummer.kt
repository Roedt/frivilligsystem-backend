package no.roedt.frivilligsystem

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Telefonnummer(
        @JsonProperty("landkode") var landkode: String = "+47",
        @JsonProperty("nummer") var nummer: Int
)