package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

// sobe um contexto para fazer a injeção de dependências (como o do client abaixo) e gerenciar os beans dentro do contexto deste teste
@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION, // trend unica do começo ao final do teste
    transactional = false
)
internal class BuscaAutoresControllerTest {

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor1: Autor

    lateinit var autor2: Autor

    @BeforeEach // rodará antes de cada teste
    internal fun setUp() {
        autorRepository.deleteAll()

        val enderecoResponse = EnderecoResponse("37500-007", "Rua Francisco Masseli", "casa")
        val endereco = Endereco(enderecoResponse, "28",)

        autor1 = Autor("Luis", "luis@zup.com.br", "Descricao de luiz", endereco)
        autorRepository.save(autor1)

        autor2 = Autor("Carlos", "carlos@zup.com.br", "Descricao de carlos", endereco)
        autorRepository.save(autor2)
    }

    @AfterEach
    internal fun tearDown() {
        print("teste teardown")
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {

        // estou usando blocante para não ter que me preocupar com reatividade, ou espera de uma resposta
        // .exchange("rota", o que devo receber de retorno da api)
        val response = client.toBlocking().exchange("/autores?email=${autor1.email}", DetalhesDosAutoresResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor1.nome, response.body()!!.nome)
        assertEquals(autor1.email, response.body()!!.email)
        assertEquals(autor1.descricao, response.body()!!.descricao)

    }

    @Test
    internal fun `deve retornar detalhe de todos autores cadastrados`() {
        val response = client.toBlocking().exchange("/autores", listOf(DetalhesDosAutoresResponse::class.java, DetalhesDosAutoresResponse::class.java)::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertFalse(2 != response.body()!!.size, "Deveria ser 2 autores retornados, mas o número foi diferente!")
    }

//    @Test
//    internal fun `deve retornar não encontrado se o autor não existir`() {
//        val response: HttpResponse<Any?> = client.toBlocking().exchange("/autores?email=autornaoexistente@gmail.com", Any::class.java)
//
//        assertEquals(HttpStatus.NOT_FOUND, response.status)
//        assertNull(response.body())
//    }
}