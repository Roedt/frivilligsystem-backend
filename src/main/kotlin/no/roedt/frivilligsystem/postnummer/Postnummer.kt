package no.roedt.frivilligsystem.postnummer

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import io.quarkus.runtime.annotations.RegisterForReflection
import javax.persistence.Cacheable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "postnummer")
@Cacheable
@RegisterForReflection
data class Postnummer(
    @Id
    var postnr: Int,
    var kommnr: Int,
    var bydel: String?,
    var kommune: String
) : PanacheEntityBase() {
    constructor() : this(
        postnr = 0,
        kommnr = 0,
        bydel = null,
        kommune = ""
    )
}