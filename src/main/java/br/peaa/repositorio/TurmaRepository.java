package br.peaa.repositorio;

import br.peaa.entidades.Curso;
import br.peaa.entidades.Turma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findTurmaByCurso (Curso codigo);

    
}
