package br.com.livraria.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Livro{
    // Declracao de variaveis
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;

    // Getters e Setters
    
    // id
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    // titulo
    public String getTitulo(){
        return titulo;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    // autor 
    public String getAutor(){
        return autor;
    }
    public void setAutor(String autor){
        this.autor = autor;
    }

    // ano de publicacao 
    public Integer getAnoPublicacao(){
        return anoPublicacao;
    }
    public void setAnoPublicacao(Integer anoPublicacao){
        this.anoPublicacao = anoPublicacao;
    }

}
