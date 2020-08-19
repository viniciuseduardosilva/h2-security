package br.com.vinicius.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vinicius.dto.ServicoPrestadoDTO;
import br.com.vinicius.model.Cliente;
import br.com.vinicius.model.ServicoPrestados;
import br.com.vinicius.repositories.ClienteRepository;
import br.com.vinicius.repositories.ServicoPrestadoRepository;
import br.com.vinicius.util.BigDecimalConvert;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {
	
	private final ClienteRepository clienteRepository;
	
	private final ServicoPrestadoRepository servicoprestadoRepository;
	
	private final BigDecimalConvert convert;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestados salvar(@RequestBody ServicoPrestadoDTO dto) {
		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		Cliente cliente = clienteRepository.findByEmail(dto.getEmail());
		ServicoPrestados servicoPrestado = new ServicoPrestados();
		servicoPrestado.setDescricao(dto.getDescricao());
		servicoPrestado.setData(data);
		servicoPrestado.setCliente(cliente);
		servicoPrestado.setValor( convert.converter(dto.getPreco()) );
		
		return servicoprestadoRepository.save(servicoPrestado);
	}
	
	@GetMapping
	public List<ServicoPrestados> pesquisar(
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value ="mes", required = false) Integer mes
			){
		return servicoprestadoRepository.findByNomeClienteAndMes("%"+nome+"%", mes);
	}

}
