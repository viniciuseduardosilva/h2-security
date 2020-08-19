package br.com.vinicius.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.vinicius.model.ServicoPrestados;

public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestados, Integer> {
	
@Query("Select s from ServicoPrestados s join s.cliente c "
		+ " where upper(c.nome) like upper(:nome) "
		+ " and MONTH(s.data) =:mes ")
List<ServicoPrestados> findByNomeClienteAndMes(@Param("nome") String nome,
												@Param ("mes") Integer mes);
	
}
