package br.com.vinicius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vinicius.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	@Autowired
	private final ClienteRepository clienteRepository;
	
	public boolean buscaEmail(String email) {
		return clienteRepository.existsByEmail(email);
	}
	
}
