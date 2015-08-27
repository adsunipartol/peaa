package br.peaa.servico;

import br.peaa.entidades.SubEvento;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface SubEventoServico {
    
    void criar(SubEvento subevento) throws ServicoException;

    void atualizar(SubEvento subevento) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    SubEvento buscar(Long codigo);

    List<SubEvento> buscarTodos();

}
