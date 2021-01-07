package de.held.demo.jpaversion.domain

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import javax.annotation.PostConstruct
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Version

@Entity
class ExampleEntity(
    @Id var id: UUID = UUID.randomUUID(),
    @Version var version: Long? = null,
    @OneToMany(cascade = [CascadeType.PERSIST]) var composite: List<Composite>
)

@Entity
class Composite(
    @Id var id: UUID = UUID.randomUUID(),
    @Version var version: Long? = null
)

@Repository
interface ExampleEntityRepository : JpaRepository<ExampleEntity, Long>

@Configuration
class ExampleConfig(
    private val repository: ExampleEntityRepository
) {
    @PostConstruct
    fun init() {
        repository.save(ExampleEntity(composite = listOf(Composite(), Composite())))
    }
}
