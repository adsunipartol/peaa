package br.peaa.servico;

import br.peaa.entidades.Pessoa;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface PessoaServico {
    
    void criar(Pessoa pessoa) throws ServicoException;

    void atualizar(Pessoa pessoa) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Pessoa buscar(Long codigo);

    List<Pessoa> buscarTodos();
    
    Pessoa buscarPorRa (String ra);
    
    List<Pessoa> buscarPessoaPorEvento (Long codEvento);

}
