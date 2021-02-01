package no.roedt.frivilligsystem

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import no.roedt.frivilligsystem.registrer.ErMedlemStatus
import javax.persistence.Cacheable
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "frivillig")
@Cacheable
@RegisterForReflection
data class Frivillig(
    val alleredeAktivILokallag: Boolean,
    val medlemIRoedt: ErMedlemStatus,
    //TODO: val kanTenkeSegAaBidraMedAktiviteter: List<Aktivitet>,
    val spesiellKompetanse: String?,
    val andreTingDuVilBidraMed: String?,
    val fortellLittOmDegSelv: String?,
) : PanacheEntity()
{
    @OneToOne
    lateinit var person: Person
}