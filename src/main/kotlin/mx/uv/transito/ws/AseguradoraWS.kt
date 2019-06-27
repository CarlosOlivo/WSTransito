package mx.uv.transito.ws

import mx.uv.transito.utils.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.Valid

private const val TAG = "La aseguradora"

@RestController
@RequestMapping("/api/aseguradora")
class AseguradoraWS(@Autowired private val aseguradoraRepository: AseguradoraRepository) {

    private val util: Util<Aseguradora, Int> = Util(aseguradoraRepository, TAG)

    @GetMapping("")
    fun getAseguradoras() = aseguradoraRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getAseguradora(@PathVariable id: Int) = util.get(id)

    @PostMapping("")
    fun newAseguradora(@Valid @RequestBody aseguradora: Aseguradora) = util.post(aseguradora, aseguradora.idAseguradora)

    @PutMapping("/{id}")
    fun editAseguradora(@PathVariable id: Int, @Valid @RequestBody aseguradora: Aseguradora) = util.put(id, aseguradora)

    @DeleteMapping("/{id}")
    fun delAseguradora(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Aseguradora(@Id
                     @GeneratedValue(strategy = GenerationType.AUTO)
                     val idAseguradora: Int,
                     var aseguradora: String)

interface AseguradoraRepository : JpaRepository<Aseguradora, Int>