package br.peaa.servico;

import br.peaa.entidades.Perfil;
import br.peaa.exceptions.ServicoException;
import java.util.List;


public interface PerfilServico {
    
    void criar(Perfil perfil) throws ServicoException;

    void atualizar(Perfil perfil) throws ServicoException;

    void remover(Long codigo) throws ServicoException;

    Perfil buscar(Long codigo);

    List<Perfil> buscarTodos();

}
