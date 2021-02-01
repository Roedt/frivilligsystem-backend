package no.roedt.frivilligsystem

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import javax.persistence.*

@RegisterForReflection
@Entity
@Table(name = "person")
@Cacheable
data class Person (
    var navn: String,
    var epost: String,
    var telefonnummer: Int, //TODO typ
    var postnummer: Int, //TODO typ
    var hypersysID: Int?,
    @Enumerated(EnumType.STRING)
    var rolle: Rolle,
    @ManyToOne
    var lokallag: Lokallag?,
) : PanacheEntity() {
  constructor(): this(
        navn = "",
        epost = "",
        telefonnummer = 0,
        postnummer = 0,
        hypersysID = null,
        rolle = Rolle.frivillig,
        lokallag = null
    )
}