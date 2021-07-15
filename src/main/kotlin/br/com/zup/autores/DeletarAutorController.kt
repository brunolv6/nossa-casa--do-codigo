package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import java.util.*
import javax.transaction.Transactional


@Controller("/autores/{id}")
class DeletarAutorController(val autorRepository: AutorRepository) {

    @Delete
    @Transactional
    fun delete(@PathVariable("id") id: Long): HttpResponse<Any> {
        val possivelAutor: Optional<Autor> = autorRepository.findById(id)

        if(possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()

        autorRepository.delete(autor)

        return HttpResponse.ok()
    }
}