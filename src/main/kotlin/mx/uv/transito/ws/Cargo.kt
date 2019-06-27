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

private const val TAG = "El cargo"

@RestController
@RequestMapping("/api/cargo")
class CargoWS(@Autowired private val cargoRepository: CargoRepository) {

    private val util: Util<Cargo, Int> = Util(cargoRepository, TAG)

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("")
    fun getCargos() = cargoRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getCargo(@PathVariable id: Int) = util.get(id)

    @PostMapping("")
    fun newCargo(@Valid @RequestBody cargo: Cargo) = util.post(cargo, cargo.idCargo)

    @PutMapping("/{id}")
    fun editCargo(@PathVariable id: Int, @Valid @RequestBody cargo: Cargo) = util.put(id, cargo)

    @DeleteMapping("/{id}")
    fun delCargo(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Cargo(@Id
                       @GeneratedValue(strategy = GenerationType.AUTO)
                       val idCargo: Int,
                       var cargo: String)

interface CargoRepository : JpaRepository<Cargo, Int>