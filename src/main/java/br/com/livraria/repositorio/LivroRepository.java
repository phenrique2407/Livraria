package br.com.livraria.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

    // O Spring cria a consulta automaticamente para verificar se já foi cadastrado um livro com o mesmo título (ignorando maiúsculas/minúsculas)
    boolean existsByTituloIgnoreCase(String titulo);
}

