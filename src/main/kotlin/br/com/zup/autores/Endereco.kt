package br.com.zup.autores

import javax.persistence.Embeddable

// isto fará com que cada atributo seja uma coluna da Entidade em que Endereco for colocada
@Embeddable
class Endereco(enderecoResponse: EnderecoResponse, numero: String) {
    val code: String = enderecoResponse.code
    val state: String = enderecoResponse.state
    val city: String = enderecoResponse.city
}
