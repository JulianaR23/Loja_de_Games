package com.example.lojadegames.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.lojadegames.model.Usuario;
import com.example.lojadegames.model.UsuarioLogin;
import com.example.lojadegames.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Optional <Usuario> cadastrarUsuario(Usuario usuario){
		
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {
			
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
			
			if(buscaUsuario.isPresent() && buscaUsuario.get().getId() != usuario.getId())
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}
		
		return Optional.empty();	
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		
		Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(buscaUsuario.isPresent()) {
			if(compararSenhas(usuarioLogin.get().getSenha(), buscaUsuario.get().getSenha())) {
				
				usuarioLogin.get().setId(buscaUsuario.get().getId());
				usuarioLogin.get().setNome(buscaUsuario.get().getNome());
				usuarioLogin.get().setFoto(buscaUsuario.get().getFoto());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(buscaUsuario.get().getSenha());
				
				return usuarioLogin;
			}
		}
		
		return Optional.empty();
	}
	
	private String gerarBasicToken(String usuario, String senha) {
		String token = usuario + ":" + senha;
		
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		
		return "Basic " + new String(tokenBase64);
	}


	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBanco);
		
	}


	private String criptografarSenha(String senha) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
	}
}
