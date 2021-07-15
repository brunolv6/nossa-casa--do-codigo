package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(val autorRepository: AutorRepository) {

    @Post
    fun cadastro(@Body @Valid request: NovoAutorRequest){

        println("Requisicao => ${request}")

        val autor = request.paraAutor()
        autorRepository.save(autor)

        println("Autor => ${autor.nome}")
    }
}