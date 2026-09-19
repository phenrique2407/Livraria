package br.com.livraria.controle;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.livraria.modelo.Livro;
import br.com.livraria.repositorio.LivroRepository;


@RestController
@RequestMapping("/livros")
public class LivroController {
    
    private final LivroRepository repositorio;

    public LivroController(LivroRepository repositorio){
        this.repositorio = repositorio;
    }

    // 1. Listar todos os livros
    @GetMapping
    public List<Livro> listarTodos() {
        return repositorio.findAll();
    }

    // Cadastrar novo Livro Sem verificacao de nome
    // @PostMapping
    // public Livro salvarNovo(@RequestBody Livro novoLivro){
    //     return repositorio.save(novoLivro);
    // }

    // 2. Cadastrar com verificacao de nome existente:
    // IF vai verificar se o titulo já existe no banco
    // retorna mensagem de erro com status 400 (Bad Request)
    // Se não existir nenhum livro com nome colocado ainda, salva normalmente e retorna HTTP 201 (Created)
    @PostMapping
    public ResponseEntity<?> salvarNovo(@RequestBody Livro novoLivro){
    if(repositorio.existsByTituloIgnoreCase(novoLivro.getTitulo())){
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Erro: Já existe um livro cadastrado com o título '" + novoLivro.getTitulo() + "'!");
    }
        Livro livroSalvo = repositorio.save(novoLivro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }
    

    // 3. Buscar Livro por ID
    @GetMapping("/{id}")
    public Livro buscarPorId(@PathVariable Long id){
        return repositorio.findById(id).orElse(null);
    }

    // 4. Atualizar Livro Existente
    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @RequestBody Livro livroAtualizado){
        Livro livroAntigo = repositorio.findById(id).orElse(null);
        if (livroAntigo != null) {
            livroAntigo.setTitulo(livroAtualizado.getTitulo());
            livroAntigo.setAutor(livroAtualizado.getAutor());
            livroAntigo.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
            return repositorio.save(livroAntigo);
        }
        return null;
    }

    // 5. Excluir Livro por ID
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        repositorio.deleteById(id);
    }
}

