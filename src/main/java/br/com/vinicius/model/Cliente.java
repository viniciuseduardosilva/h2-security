package br.com.vinicius.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

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
public class Cliente {
	@Id
	@Column(length = 100)
	private String email;
	
	@Column(length = 100)
	private String nome;
	
	@Column(length = 100)
	private String telefone;
	
	@Column(updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate cadastro;
	
	@PrePersist
	public void dataCadastro() {
		this.setCadastro(LocalDate.now());
	}
}
