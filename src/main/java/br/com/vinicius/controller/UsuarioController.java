package br.com.vinicius.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vinicius.dto.ClienteDTO;
import br.com.vinicius.model.Cliente;
import br.com.vinicius.model.Usuario;
import br.com.vinicius.model.response.MessageResponse;
import br.com.vinicius.repositories.ClienteRepository;
import br.com.vinicius.repositories.UsuarioRepository;
import br.com.vinicius.service.ClienteDtoService;
import br.com.vinicius.service.ClienteService;
import br.com.vinicius.service.JwtService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final ClienteService clienteService;
	private final PasswordEncoder passwordEncoder;
	private final ClienteDtoService clienteDtoService;
	private final JwtService jwtService;
	
	
	private final UsuarioRepository usuarioRepository;
	private final ClienteRepository clienteRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CHECKPOINT)
	public ResponseEntity<MessageResponse> cadastrar(@RequestBody ClienteDTO clienteDto) {
		if (clienteService.buscaEmail(clienteDto.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario já cadastrado!");
		}
		String senhaCodificada = passwordEncoder.encode(clienteDto.getSenha());
		clienteDto.setSenha(senhaCodificada);
		clienteDtoService.salvaClienteDto(
				Usuario.builder()
					.username(clienteDto.getEmail())
					.password(senhaCodificada)
					.roles("USER")
					.build(),
				Cliente.builder()
					.email(clienteDto.getEmail())
					.telefone(clienteDto.getTelefone())
					.nome(clienteDto.getNome())
					.build());
		return new ResponseEntity<MessageResponse>(new MessageResponse("Email Enviado com Sucesso!!"), HttpStatus.OK);
	}
	
	@GetMapping("/confirmacao/{token}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<MessageResponse> confirmacao(@PathVariable String token){
		boolean isValid = jwtService.tokenValido(token);
		
		if(isValid) {
			String username = jwtService.obterLogin(token);
			clienteDtoService.validaUsuario(username);
			return new ResponseEntity<MessageResponse>(new MessageResponse("Cadastrado com Sucesso!!")
					, HttpStatus.CREATED);
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cliente não cadasrtrado!");
	}

	@GetMapping
	public List<Usuario> teste(){
		return usuarioRepository.findAll();
	}
	
	@GetMapping("/teste")
	public List<Cliente> teste2(){
		return clienteRepository.findAll();
	}
	
}
