package mx.uv.transito.ws

import mx.uv.transito.utils.Util
import org.hibernate.annotations.CreationTimestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.*
import javax.validation.Valid

private const val TAG = "El dictamen"

@RestController
@RequestMapping("/api/dictamen")
class DictamenWS(@Autowired private val dictamenRepository: DictamenRepository) {

    private val util: Util<Dictamen, Int> = Util(dictamenRepository, TAG)

    @GetMapping("")
    fun getDictamenes() = dictamenRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getDictamen(@PathVariable id: Int) = util.get(id)

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("")
    fun newDictamen(@Valid @RequestBody dictamen: Dictamen) = util.post(dictamen, dictamen.idDictamen)

    @PutMapping("/{id}")
    fun editDictamen(@PathVariable id: Int, @Valid @RequestBody dictamen: Dictamen) = util.put(id, dictamen)

    @DeleteMapping("/{id}")
    fun delDictamen(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Dictamen(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    val idDictamen: Int,
                    var descripcion: String,
                    @CreationTimestamp
                    @Temporal(TemporalType.TIMESTAMP)
                    var fechaHora: Date?,
                    var idPersonal: Int)

interface DictamenRepository : JpaRepository<Dictamen, Int>