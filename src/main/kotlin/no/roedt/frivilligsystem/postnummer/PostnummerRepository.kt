package no.roedt.frivilligsystem.postnummer

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PostnummerRepository : PanacheRepositoryBase<Postnummer, Int>