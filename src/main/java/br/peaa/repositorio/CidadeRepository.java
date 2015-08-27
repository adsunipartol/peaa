package br.peaa.repositorio;

import br.peaa.entidades.Cidade;
import br.peaa.entidades.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

   
    List<Cidade> findCidadeByEstado (Estado codigo);
    
}
