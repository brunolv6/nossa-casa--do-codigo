package br.com.zup.autores

class DetalhesDosAutoresResponse(autor: Autor) {

    val nome = autor.nome
    val email = autor.email
    val descricao = autor.descricao

}
