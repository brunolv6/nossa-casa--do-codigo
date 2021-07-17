package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

// tenho que subir a aplicacao para termos o contexto do Micronaut
@MicronautTest
internal class CadastraAutorControllerTest{

    // injetar uma instancia de correioApi para podermos manipular seu comportamento
    @field:Inject
    lateinit var correioApi: CorreioApi

    // lateinit var -> significa que ele inicializará só depois, mais tarde
    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve cadastrar novo autor`() {

        // cenario
        val novoAutorRequest = NovoAutorRequest("Luis", "luis@zup.com.br", "Descricao de luiz", "12914450", "FPZ2032")

        var enderecoResponse = EnderecoResponse("323", "Rio de", "Navera")

        // quando este método da api externa for chamado, devemos retornar um HttpResponse do tipo ok com o corpo do tipo enderecoResponse
        Mockito.`when`(correioApi.pegarEndereco(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        // construcao do request para a requisicao que sera testada
        val request = HttpRequest.POST("/autores", novoAutorRequest)

        // acao - requisicao para teste
        val response = client.toBlocking().exchange(request, Any::class.java)

        // corretude - verificacao
        assertEquals(HttpStatus.CREATED, response.status)

        assertTrue(response.headers.contains("Location"))

        // entender o Erro!
        // assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))

    }

    // quando no meio do teste uma instancia de CorreioApi for utilizada, utilizaremos esta
    // funcionando como um @Replaces ??
    @MockBean(CorreioApi::class)
    fun correioMock(): CorreioApi {
        return Mockito.mock(CorreioApi::class.java)
    }
}