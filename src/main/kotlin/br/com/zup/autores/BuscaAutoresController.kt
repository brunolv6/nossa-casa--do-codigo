package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue


@Controller("/autores")
class BuscaAutoresController(val autorRepository: AutorRepository) {

    // opcional autores/?email=qualquer@gmail.com
    // como coloquei defaultValue vazio, então não é obrigatório
    @Get
    fun lista(@QueryValue(defaultValue = "") email: String) : HttpResponse<Any>{

        if(email.isEmpty()){
            val autores: List<Autor>  = autorRepository.findAll()

            val response = autores.map{ autor -> DetalhesDosAutoresResponse(autor) }

            return HttpResponse.ok(response)
        }

        val possivelAutor = autorRepository.findByEmail(email)

        if(possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()

        return HttpResponse.ok(DetalhesDosAutoresResponse(autor))

    }
}