package br.peaa.servico;

import br.peaa.entidades.Evento;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface EventoServico {
    
    void criar(Evento evento) throws ServicoException;

    void atualizar(Evento evento) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Evento buscar(Long codigo);

    List<Evento> buscarTodos();

}
