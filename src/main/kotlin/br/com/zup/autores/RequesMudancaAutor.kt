package br.com.zup.autores

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Introspected
class RequesMudancaAutor(
    val nome: String? = null,
    @field:Email val email: String? = null,
    @field:Size(max = 400) val descricao: String? = null
)
