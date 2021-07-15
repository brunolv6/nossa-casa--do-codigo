package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get


@Controller("/autores")
class BuscaAutoresController(val autorRepository: AutorRepository) {

    @Get
    fun lista() : HttpResponse<List<DetalhesDosAutoresResponse>>{
        val autores: List<Autor> = autorRepository.findAll()

        val response = autores.map{ autor -> DetalhesDosAutoresResponse(autor) }

        return HttpResponse.ok(response)

    }
}