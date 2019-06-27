package mx.uv.transito.ws

import mx.uv.transito.utils.Mensaje
import mx.uv.transito.utils.Util
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.hibernate.annotations.CreationTimestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.support.ServletContextResource
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.persistence.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

private const val TAG = "El reporte"

@RestController
@RequestMapping("/api/reporte")
class ReporteWS(@Autowired private val reporteRepository: ReporteRepository) {

    private val util: Util<Reporte, Int> = Util(reporteRepository, TAG)
    private val IMGS_PATH = "imgs"

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("")
    fun getReportes() = reporteRepository.findAll()

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/{id}")
    fun getReporte(@PathVariable id: Int) = util.get(id)

    @GetMapping("/conductor/{id}")
    fun getReportesByConductor(@PathVariable id: Int) = reporteRepository.findByIdConductor(id)

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping("/img/{idReporte}/{img}", produces = ["image/jpeg"])
    @ResponseBody
    fun getImg(@PathVariable("idReporte") idReporte: Int, @PathVariable("img") img: Int): ByteArray {
        return Files.readAllBytes(Paths.get(IMGS_PATH, "${idReporte}_${img}.jpg"))
    }

    @PostMapping("")
    fun newReporte(@Valid @RequestBody reporte: Reporte) = util.post(reporte, reporte.idReporte)

    @PostMapping("/img", consumes = ["multipart/form-data"])
    fun newImg(@RequestParam("idReporte") idReporte: Int, @RequestParam("img") img: MultipartFile): ResponseEntity<Mensaje> {
        val reporteGET = reporteRepository.findById(idReporte)
        return if(reporteGET.isPresent) {
            val reporte = reporteGET.get()
            if(++reporte.imgs > 8) {
                ResponseEntity<Mensaje>(Mensaje("Número de imagenes por reporte excedido", ""), HttpStatus.FORBIDDEN)
            } else {
                if(!img.isEmpty) {
                    if(Files.notExists(Paths.get(IMGS_PATH))) {
                        Files.createDirectory(Paths.get(IMGS_PATH))
                    }
                    if(Files.copy(img.inputStream, Paths.get(IMGS_PATH, "${reporte.idReporte}_${reporte.imgs}.jpg")) != 0L) {
                        reporteRepository.save(reporte)
                        ResponseEntity<Mensaje>(Mensaje("Imagen guardada correctamente", ""), HttpStatus.OK)
                    } else {
                        ResponseEntity<Mensaje>(Mensaje("No se puede guardar la imagen en el servidor", ""), HttpStatus.INTERNAL_SERVER_ERROR)
                    }
                } else {
                    ResponseEntity<Mensaje>(Mensaje("Imagen invalida", ""), HttpStatus.BAD_REQUEST)
                }
            }

        } else {
            ResponseEntity<Mensaje>(Mensaje("Reporte invalido", ""), HttpStatus.BAD_REQUEST)
        }
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping("/{id}")
    fun editReporte(@PathVariable id: Int, @Valid @RequestBody reporte: Reporte) = util.put(id, reporte)

    @DeleteMapping("/{id}")
    fun delReporte(@PathVariable id: Int) = util.del(id)
}

@Entity
data class Reporte(@Id
                   @GeneratedValue(strategy = GenerationType.AUTO)
                   val idReporte: Int,
                   var idConductor: Int,
                   var idImplicado: Int,
                   var idDictamen: Int? = null,
                   var imgs: Int = 0,
                   var placas: String,
                   var latitud: Double,
                   var longitud: Double,
                   @CreationTimestamp
                   @Temporal(TemporalType.TIMESTAMP)
                   var fechaHora: Date?)


interface ReporteRepository : JpaRepository<Reporte, Int> {
    fun findByIdConductor(idConductor: Int): List<Reporte>?
}