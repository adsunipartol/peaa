package br.peaa.servico;

import br.peaa.entidades.Entidade;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface EntidadeServico {
    
    void criar(Entidade entidade) throws ServicoException;

    void atualizar(Entidade entidade) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Entidade buscar(Long codigo);

    List<Entidade> buscarTodos();

}
