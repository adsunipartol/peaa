package br.peaa.repositorio;

import br.peaa.entidades.Pessoa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findPessoaByRa (String ra);
    
    
    @Query ("select p from Pessoa p inner join p.eventos e where e.codigo = :codigo")
    List<Pessoa> ParticipacaoEventos (@Param("codigo")Long codEvento);
    
}
