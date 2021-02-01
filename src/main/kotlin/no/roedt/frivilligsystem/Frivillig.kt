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
    var alleredeAktivILokallag: Boolean,
    var medlemIRoedt: ErMedlemStatus,
    //TODO: val kanTenkeSegAaBidraMedAktiviteter: List<Aktivitet>,
    var spesiellKompetanse: String?,
    var andreTingDuVilBidraMed: String?,
    var fortellLittOmDegSelv: String?,
) : PanacheEntity()
{
    @OneToOne
    lateinit var person: Person
}