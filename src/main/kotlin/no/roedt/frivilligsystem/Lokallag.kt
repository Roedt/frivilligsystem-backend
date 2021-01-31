package no.roedt.frivilligsystem

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import javax.persistence.Cacheable
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "lokallag")
@Cacheable
@RegisterForReflection
data class Lokallag(
        var name: String
): PanacheEntity() {
    constructor() : this("")

//    @OneToMany(mappedBy = "lokallag")
  //  val frivillige: MutableSet<Frivillig> = HashSet()
}
