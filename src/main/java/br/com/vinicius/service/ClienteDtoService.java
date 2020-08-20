package br.com.vinicius.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.vinicius.model.Cliente;
import br.com.vinicius.model.Usuario;
import br.com.vinicius.repositories.ClienteRepository;
import br.com.vinicius.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteDtoService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final ClienteRepository clienteRepository;
	
	private final JwtService jwtService;
	
	private final EmailService emailService;
	
	public void salvaClienteDto(Usuario usuario, Cliente cliente) {
		usuarioRepository.save(usuario);
		clienteRepository.save(cliente);
		
		String token = jwtService.gerarToken(usuario);
		emailService.sendEmail(cliente.getEmail(), token);
	}
	
	public void validaUsuario(String username) {
		usuarioRepository.findByUsername(username).map(usuario ->{
			usuario.setRoles("USER");
			return usuarioRepository.save(usuario);
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

}
