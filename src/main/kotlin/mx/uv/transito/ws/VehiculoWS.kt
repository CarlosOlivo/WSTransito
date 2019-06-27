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

private const val TAG = "El vehiculo"

@RestController
@RequestMapping("/api/vehiculo")
class VehiculoWS(@Autowired private val vehiculoRepository: VehiculoRepository) {

    private val util: Util<Vehiculo, String> = Util(vehiculoRepository, TAG)

    @GetMapping("")
    fun getVehiculos() = vehiculoRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getVehiculo(@PathVariable id: String) = util.get(id)

    @GetMapping("/conductor/{id}")
    fun getVehiculoByConductor(@PathVariable id: Int) = vehiculoRepository.findByIdConductor(id)

    @PostMapping("")
    fun newVehiculo(@Valid @RequestBody vehiculo: Vehiculo) = util.post(vehiculo, vehiculo.placas)

    @PutMapping("/{id}")
    fun editVehiculo(@PathVariable id: String, @Valid @RequestBody vehiculo: Vehiculo) = util.put(id, vehiculo)

    @DeleteMapping("/{id}")
    fun delVehiculo(@PathVariable id: String) = util.del(id)
}

@Entity
data class Vehiculo(@Id
                    val placas: String,
                    var idConductor: Int,
                    var idAseguradora: Int? = null,
                    var marca: String,
                    var modelo: String,
                    var anio: Int,
                    var color: String,
                    var poliza: String? = null)

interface VehiculoRepository : JpaRepository<Vehiculo, String> {
    fun findByIdConductor(idConductor: Int): List<Vehiculo>?
}