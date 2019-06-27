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

private const val TAG = "El personal"

@RestController
@RequestMapping("/api/personal")
class PersonalWS(@Autowired private val personalRepository: PersonalRepository) {

    private val util: Util<Personal, Int> = Util(personalRepository, TAG)

    @GetMapping("")
    fun getAllPersonal() = personalRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getPersonal(@PathVariable id: Int) = util.get(id)

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("/acceso")
    fun getConductorAcceso(@RequestBody fields: Map<String, String>) =
            personalRepository.findByUsuarioAndContrasenia(
                    fields.getOrDefault("usuario", ""),
                    fields.getOrDefault("contrasenia", ""))

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping("")
    fun newPersonal(@Valid @RequestBody personal: Personal) = util.post(personal, personal.idPersonal)

    @PutMapping("/{id}")
    fun editPersonal(@PathVariable id: Int, @Valid @RequestBody personal: Personal) = util.put(id, personal)

    @DeleteMapping("/{id}")
    fun delPersonal(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Personal(@Id
                     @GeneratedValue(strategy = GenerationType.AUTO)
                     val idPersonal: Int,
                     var idCargo: Int,
                     var nombre: String,
                     var usuario: String,
                     var contrasenia: String)


interface PersonalRepository : JpaRepository<Personal, Int> {
    fun findByUsuarioAndContrasenia(usuario: String, contrasenia: String): Personal?
}