package br.peaa.servico;

import br.peaa.entidades.Curso;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface CursoServico {
    
    void criar(Curso curso) throws ServicoException;

    void atualizar(Curso curso) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Curso buscar(Long codigo);

    List<Curso> buscarTodos();
    
    Curso buscarPorNome (String nome);
    
    List<Curso> buscarPorCaractere (String parametro);
    

}
