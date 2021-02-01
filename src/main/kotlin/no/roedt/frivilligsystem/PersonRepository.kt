package no.roedt.frivilligsystem

import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PersonRepository : PanacheRepository<Person> {

    fun save(person: Person) {
        if (find("epost", person.epost).count() > 0L) {
            update(
                "navn = '${person.navn}', " +
                        "hypersysID = ${person.hypersysID}," +
                        "telefonnummer = ${person.telefonnummer}," +
                        "postnummer = ${person.postnummer}," +
                        "rolle = '${person.rolle.name}'," +
                        "lokallag = ${person.lokallag} " +
                        "where epost = '${person.epost}'"
            )
        }
        else {
            persist(person)
        }
    }
}