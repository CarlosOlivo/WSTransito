package mx.uv.transito.utils

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class Util<T, ID>(private val repository: JpaRepository<T, ID>, private val TAG: String) {
    fun get(id: ID): ResponseEntity<Any?> {
        val entity = repository.findById(id)
        return if (entity.isPresent) {
            ResponseEntity(entity.get(), HttpStatus.OK)
        } else {
            ResponseEntity(Mensaje("$TAG no existe", "$TAG con el ID $id no existe"), HttpStatus.NOT_FOUND)
        }
    }

    fun post(entity: T, id: ID): ResponseEntity<Any?> {
        return if (repository.existsById(id))
            ResponseEntity(Mensaje("$TAG ya existe", "$TAG con el ID $id ya existe"), HttpStatus.FOUND)
        else
            ResponseEntity(repository.save(entity), HttpStatus.OK)
    }

    fun put(id: ID, entity: T): ResponseEntity<Any?> {
        val entityBD = repository.findById(id)
        return if (entityBD.isPresent) {
            ResponseEntity(repository.save(entity), HttpStatus.OK)
        } else {
            ResponseEntity(Mensaje("$TAG no existe", "$TAG con el ID $id no existe"), HttpStatus.NOT_FOUND)
        }
    }

    /*@PatchMapping("/{id}")
    fun patchConductor(@PathVariable id: Int, @Valid @RequestBody fields: Map<String, Any>): ResponseEntity<Any> {
        if (id < 0 || fields.isEmpty() || fields["idConductor"] != id) {
            return ResponseEntity(Mensaje("Petición invalida", "No hay campos o ID invalido"), HttpStatus.BAD_REQUEST)
        }
        val conductor = conductorRepository.findById(id)
        return if (conductor.isPresent) {
            fields.minus("idConductor").forEach { (k, v) ->
                val field = ReflectHelper.findField(Conductor::class.java, k)
                field.isAccessible = true
                if(field.type != Date::class.java) {
                    ReflectionUtils.setField(field, conductor.get(), v)
                } else {
                    ReflectionUtils.setField(field, conductor.get(), SimpleDateFormat("dd/MM/yyyy").parse(v as String?))
                }
            }
            ResponseEntity(conductorRepository.save(conductor.get()), HttpStatus.OK)
        } else {
            ResponseEntity(Mensaje("Conductor no existe", "Conductor con ID $id no existe"), HttpStatus.NOT_FOUND)
        }
    }*/

    fun del(id: ID): ResponseEntity<Any> {
        repository.deleteById(id)
        return ResponseEntity(Mensaje("$TAG con el ID $id fue eliminado correctamente", ""), HttpStatus.OK)
    }
}

data class Mensaje(val mensaje: String,
                   val error: String)