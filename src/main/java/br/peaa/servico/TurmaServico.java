package br.peaa.servico;

import br.peaa.entidades.Turma;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface TurmaServico {
    
    void criar(Turma turma) throws ServicoException;

    void atualizar(Turma turma) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Turma buscar(Long codigo);

    List<Turma> buscarTodos();
    
    List<Turma> buscarTurmaPorCurso(Long cod_curso);


    
}
