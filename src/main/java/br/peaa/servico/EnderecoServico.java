package br.peaa.servico;

import br.peaa.entidades.Endereco;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface EnderecoServico {
    
    void criar(Endereco endereco) throws ServicoException;

    void atualizar(Endereco endereco) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Endereco buscar(Long codigo);

    List<Endereco> buscarTodos();

}
