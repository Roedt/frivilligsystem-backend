package no.roedt.frivilligsystem.registrer

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import no.roedt.frivilligsystem.PostnummerDTO
import no.roedt.frivilligsystem.Telefonnummer

@RegisterForReflection
data class RegistrerNyFrivilligRequest(
    @JsonProperty("navn") var navn: String,
    @JsonProperty("epost") var epost: String,
    @JsonProperty("telefonnummer") var telefonnummer: Telefonnummer,
    @JsonProperty("postnummer") var postnummer: PostnummerDTO,
    @JsonProperty("alleredeAktivILokallag") var alleredeAktivILokallag: Boolean,
    @JsonProperty("medlemIRoedt") var medlemIRoedt: ErMedlemStatus,
    @JsonProperty("kanTenkeSegAaBidraMedAktiviteter") var kanTenkeSegAaBidraMedAktiviteter: List<Aktivitet>,
    @JsonProperty("spesiellKompetanse") var spesiellKompetanse: String?,
    @JsonProperty("andreTingDuVilBidraMed") var andreTingDuVilBidraMed: String?,
    @JsonProperty("fortellLittOmDegSelv") var fortellLittOmDegSelv: String?
)