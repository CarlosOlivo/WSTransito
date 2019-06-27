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

private const val TAG = "El implicado"

@RestController
@RequestMapping("/api/implicado")
class ImplicadoWS(@Autowired private val implicadoRepository: ImplicadoRepository) {

    private val util: Util<Implicado, Int> = Util(implicadoRepository, TAG)

    @GetMapping("")
    fun getImplicados() = implicadoRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getImplicado(@PathVariable id: Int) = util.get(id)

    @PostMapping("")
    fun newImplicado(@Valid @RequestBody implicado: Implicado) = util.post(implicado, implicado.idImplicado)

    @PutMapping("/{id}")
    fun editImplicado(@PathVariable id: Int, @Valid @RequestBody implicado: Implicado) = util.put(id, implicado)

    @DeleteMapping("/{id}")
    fun delImplicado(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Implicado(@Id
                     @GeneratedValue(strategy = GenerationType.AUTO)
                     val idImplicado: Int,
                     var idAseguradora: Int? = null,
                     var nombre: String? = null,
                     var placas: String,
                     var poliza: String? = null,
                     var marca: String,
                     var modelo: String,
                     var color: String)

interface ImplicadoRepository : JpaRepository<Implicado, Int>