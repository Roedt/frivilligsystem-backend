package no.roedt.frivilligsystem

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import no.roedt.frivilligsystem.registrer.ErMedlemStatus
import javax.persistence.*

@Entity
@Table(name = "frivillig")
@Cacheable
@RegisterForReflection
data class Frivillig(
    val navn: String,
    val epost: String,
    val telefonnummer: Int, //TODO typ
    val postnummer: Int, //TODO typ
    @ManyToOne
    val lokallag: Lokallag?,
    val alleredeAktivILokallag: Boolean,
    val medlemIRoedt: ErMedlemStatus,
    //TODO: val kanTenkeSegAaBidraMedAktiviteter: List<Aktivitet>,
    val spesiellKompetanse: String?,
    val andreTingDuVilBidraMed: String?,
    val fortellLittOmDegSelv: String?,
    val hypersysID: Int?,
    @Enumerated(EnumType.STRING)
    val rolle: Rolle
) : PanacheEntity()
