package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client

@Client("https://ws.apicep.com/cep")
interface CorreioApi {

    @Get("/{cep}.json")
    fun pegarEndereco(cep: String): HttpResponse<EnderecoResponse>
}