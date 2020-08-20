package br.com.vinicius.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicoPrestados {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descricao;
	
	//relação da classe para com o objeto 
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	private BigDecimal valor;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;

}
