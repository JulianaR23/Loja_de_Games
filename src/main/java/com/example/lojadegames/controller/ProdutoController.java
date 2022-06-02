package com.example.lojadegames.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lojadegames.model.Produto;
import com.example.lojadegames.repository.CategoriaRepository;
import com.example.lojadegames.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity <List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity <List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/menor_valor/{preco}")
	public ResponseEntity <List<Produto>> getPrecoMenor(@PathVariable BigDecimal preco){
		return ResponseEntity.ok(produtoRepository.findByPrecoLessThan(preco));
	}
	
	@GetMapping("/maior_valor/{preco}")
	public ResponseEntity <List<Produto>> getPrecoMaior(@PathVariable BigDecimal preco){
		return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThan(preco));
	}
		
	@PostMapping
	public ResponseEntity <Produto> postProduto(@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity <Produto> putProduto(@Valid @RequestBody Produto produto){
		if(produtoRepository.existsById(produto.getId())) {
			if(categoriaRepository.existsById(produto.getCategoria().getId()))
					return ResponseEntity.status(HttpStatus.OK)
							.body(produtoRepository.save(produto));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> { produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}

}
