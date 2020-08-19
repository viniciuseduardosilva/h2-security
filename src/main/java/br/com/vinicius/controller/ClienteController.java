package br.com.vinicius.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vinicius.model.Cliente;
import br.com.vinicius.repositories.ClienteRepository;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody Cliente cliente) {
		
		return clienteRepository.save(cliente);
	}
	
	@GetMapping
	public List<Cliente> obterTodos(){
		return clienteRepository.findAll();
	}
	
	@GetMapping("{email}")
	public Cliente buscaCliente(@PathVariable String email) {
		return clienteRepository.findByEmail(email);
	}
	
	@DeleteMapping("{email}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		clienteRepository.delete(cliente);
	}
	

}
