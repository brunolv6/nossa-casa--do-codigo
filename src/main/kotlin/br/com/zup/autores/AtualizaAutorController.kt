package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import java.util.*
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class AtualizaAutorController(val autorRepository: AutorRepository) {

    @Put("/{id}")
    @Transactional
    fun mudarNome(@PathVariable("id") id: Long, @Body @Valid request: RequesMudancaAutor): HttpResponse<Any>{

        val possivelAutor: Optional<Autor> = autorRepository.findById(id)

        if(possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()

        with(autor){
            nome = request.nome ?: nome
            email = request.email ?: email
            descricao = request.descricao ?: descricao
        }

// não necessário porque este autor já é gerenciado pelo Hibernate, logo, quando a transação finalizar (@Transaction que permite isto), automaticamente ele será atualizado no banco de dados
//        autorRepository.update(autor)

        return HttpResponse.ok(DetalhesDosAutoresResponse(autor))
    }

}