package mx.uv.transito.ws

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.OptBoolean
import mx.uv.transito.utils.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.*
import javax.validation.Valid

private const val TAG = "El conductor"

@RestController
@RequestMapping("/api/conductor")
class ConductorWS(@Autowired private val conductorRepository: ConductorRepository) {

    private val util: Util<Conductor, Int> = Util(conductorRepository, TAG)

    @GetMapping("")
    fun getConductores() = conductorRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getConductor(@PathVariable id: Int) = util.get(id)

    @PostMapping("/acceso")
    fun getConductorAcceso(@RequestBody fields: Map<String, String>) =
            conductorRepository.findByNumCelularAndContrasenia(
                    fields.getOrDefault("numCelular", ""),
                    fields.getOrDefault("contrasenia", ""))

    @PostMapping("")
    fun newConductor(@Valid @RequestBody conductor: Conductor) = util.post(conductor, conductor.idConductor)

    @PutMapping("/{id}")
    fun editConductor(@PathVariable id: Int, @Valid @RequestBody conductor: Conductor) = util.put(id, conductor)

    @DeleteMapping("/{id}")
    fun delConductor(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Conductor(@Id
                     @GeneratedValue(strategy = GenerationType.AUTO)
                     val idConductor: Int,
                     var nombre: String,
                     var apellidoPaterno: String,
                     var apellidoMaterno: String,
                     @Temporal(TemporalType.DATE)
                     @JsonFormat(pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE)
                     @DateTimeFormat(pattern = "dd/MM/yyyy")
                     var fechaNacimiento: Date,
                     var numLicencia: Int,
                     var numCelular: String,
                     var contrasenia: String)

interface ConductorRepository : JpaRepository<Conductor, Int> {
    fun findByNumCelularAndContrasenia(numCelular: String, contrasenia: String): Conductor?
}