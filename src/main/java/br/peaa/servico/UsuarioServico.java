package br.peaa.servico;

import br.peaa.entidades.Usuario;
import br.peaa.exceptions.ServicoException;
import java.util.List;

public interface UsuarioServico {

    void criar(Usuario usuario) throws ServicoException;

    void atualizar(Usuario usuario) throws ServicoException;

    void remover(Long codigo) throws ServicoException;
    
    Usuario validarUsuario (String login, String senha) throws ServicoException;

    Usuario buscar(Long codigo);
    
    Usuario buscarPorLogin (String login);

    List<Usuario> buscarTodos();

}
