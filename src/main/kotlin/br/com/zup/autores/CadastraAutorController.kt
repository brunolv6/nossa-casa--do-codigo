package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(val autorRepository: AutorRepository, val correioApi: CorreioApi) {

    @Post
    @Transactional
    fun cadastro(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {

        println("Requisicao => ${request}")

        // pegar dados do cep de api externa
        val enderecoResponse: HttpResponse<EnderecoResponse> = correioApi.pegarEndereco(request.cep)

        if(enderecoResponse.body() == null) return HttpResponse.badRequest()

        val autor = request.paraAutor(enderecoResponse.body()!!)

        autorRepository.save(autor)

        println("Autor Endereco => ${autor.endereco}")

        val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))

        return HttpResponse.created(uri)
    }
}