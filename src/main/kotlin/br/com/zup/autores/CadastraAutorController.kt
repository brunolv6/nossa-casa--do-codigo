package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/autores")
class CadastraAutorController {

    @Post
    fun cadastro(@Body request: NovoAutorRequest){
        println(request)
    }
}