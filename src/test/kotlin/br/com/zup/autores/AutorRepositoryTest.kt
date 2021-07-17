package br.com.zup.autores

import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false,
    transactionMode = TransactionMode.SINGLE_TRANSACTION,
    transactional = false
)
class AutorRepositoryTest {

    @Inject
    lateinit var autorRepository: AutorRepository

    lateinit var autor: Autor

    @BeforeEach
    fun setUp(){
        autorRepository.deleteAll()

        val enderecoResponse = EnderecoResponse("37500-007", "Rua Francisco Masseli", "casa")
        val endereco = Endereco(enderecoResponse, "28",)

        autor = Autor("Luis", "luis@zup.com.br", "Descricao de luiz", endereco)
    }

    @AfterEach
    fun tearDown(){
        autorRepository.deleteAll()
    }

    @Test
    internal fun `devo inserir um novo autor`() {
        autorRepository.save(autor)

        Assertions.assertEquals(1, autorRepository.count())
    }

    @Test
    internal fun `devo encontrar um autor a partir do email`() {
        autorRepository.save(autor)

        val possivelAutor = autorRepository.findByEmail(autor.email)

        Assertions.assertTrue(possivelAutor.isPresent())

        val autorReturned = possivelAutor.get()

        Assertions.assertEquals(autor.nome, autorReturned.nome)
        Assertions.assertEquals(autor.email, autorReturned.email)
        Assertions.assertEquals(autor.descricao, autorReturned.descricao)
    }
}