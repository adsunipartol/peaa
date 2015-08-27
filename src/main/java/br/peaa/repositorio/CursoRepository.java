package br.peaa.repositorio;

import br.peaa.entidades.Curso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findCursoByNome (String nome);
    
    List<Curso> findCursoByNomeLike (String parametro);
}
