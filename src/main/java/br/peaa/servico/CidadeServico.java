package br.peaa.servico;

import br.peaa.entidades.Cidade;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface CidadeServico {
    
    void criar(Cidade cidade) throws ServicoException;

    void atualizar(Cidade cidade) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Cidade buscar(Long codigo);

    List<Cidade> buscarTodos();

    List<Cidade> buscarCidadePorEstado(Long cod_estado);
    
}
