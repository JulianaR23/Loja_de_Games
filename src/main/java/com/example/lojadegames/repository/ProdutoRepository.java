package com.example.lojadegames.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.lojadegames.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Long> {
	public List <Produto> findAllByNomeContainingIgnoreCase (@Param("nome")String nome);
	
	public List <Produto> findByPrecoLessThan (BigDecimal preco);
	
	public List <Produto> findByPrecoGreaterThan (BigDecimal preco);
}
