package br.com.vinicius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vinicius.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
	
	
	Cliente findByEmail(String email);
	
	
}

