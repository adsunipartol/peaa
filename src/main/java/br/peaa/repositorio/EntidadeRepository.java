package br.peaa.repositorio;

import br.peaa.entidades.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntidadeRepository extends JpaRepository<Entidade, Long> {

}
