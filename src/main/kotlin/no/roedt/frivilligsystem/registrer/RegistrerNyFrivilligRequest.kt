package no.roedt.frivilligsystem.registrer

import io.quarkus.runtime.annotations.RegisterForReflection
import no.roedt.ringesentralen.Postnummer
import no.roedt.ringesentralen.Telefonnummer

@RegisterForReflection
data class RegistrerNyFrivilligRequest(
    val navn: String,
    val epost: String,
    val telefonnummer: Telefonnummer,
    val postnummer: Postnummer,
    val alleredeAktivILokallag: Boolean,
    val medlemIRoedt: ErMedlemStatus,
    val kanTenkeSegAaBidraMedAktiviteter: List<Aktivitet>,
    val spesiellKompetanse: String?,
    val andreTingDuVilBidraMed: String?,
    val fortellLittOmDegSelv: String?
)