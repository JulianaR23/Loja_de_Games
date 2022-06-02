package com.example.lojadegames.model;


import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo nome é obrigatório e não pode utilizar espaços em brancos!")
	@Size(min = 5, max = 1000, message = "O atributo nome deve conter no mínino 5 e no máximo 1000 caracteres")
	private String nome;
	
	@NotBlank(message = "O atributo descrição é obrigatório e não pode utilizar espaços em brancos!")
	@Size(min = 10, max = 1000, message = "O atributo descrição deve conter no mínino 10 e no máximo 1000 caracteres")
	private String descricao;
	
	@NotNull(message = "O atributo foto deverá conter!")
	private String foto;
	
	@NotNull(message = "O atributo data de lançamento deverá conter!")
	private String dataLancamento;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull(message = "O atributo preço é obrigatório!")
	@Positive(message = "O atributo preço deve ser maior do que zero!")
	private BigDecimal preco;
	
	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;
	

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	
	
	
}
