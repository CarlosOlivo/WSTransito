package mx.uv.transito.utils

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class Error {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MissingKotlinParameterException::class)
    fun faltaCampo(mkpEx: MissingKotlinParameterException) = Mensaje("Falta el campo ${mkpEx.parameter.name.orEmpty()} en la peticion", mkpEx.localizedMessage)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(InvalidFormatException::class)
    fun fechaInvalida(ifEx: InvalidFormatException) = Mensaje("La fecha ${ifEx.value} tiene un formato invalido, se acepta el formato dd/MM/yyyy", ifEx.localizedMessage)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(JsonParseException::class)
    fun jsonInvalido(jsEx: JsonParseException) = Mensaje("La peticion JSON tiene un formato invalido", jsEx.localizedMessage)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(NumberFormatException::class)
    fun numeroInvalido(numberFormatException: NumberFormatException) = Mensaje("La peticion tiene un número invalido", numberFormatException.localizedMessage)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun datoInexistente(erdaEx: EmptyResultDataAccessException) = Mensaje("No existe el elemento", erdaEx.localizedMessage)

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun datoReferencia(divE: DataIntegrityViolationException) = Mensaje("No se puede realizar la operación por una restricción, elimine los elementos referenciados para continuar", divE.mostSpecificCause.localizedMessage)
}