package br.peaa.servico;

import br.peaa.entidades.Usuario;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.UsuarioRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("usuarioServico")
@Transactional(readOnly = false)
public class UsuarioServicoImpl implements UsuarioServico {

    private static final Logger logger = Logger.getLogger(UsuarioServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Override
    @Transactional(readOnly = false)
    public void criar(Usuario usuario) throws ServicoException {
        try {
            if (repositorioUsuario.findByLogin(usuario.getLogin()) != null) {
                throw new Exception("Já existe usuario com login " + usuario.getLogin());
                
            }
            repositorioUsuario.save(usuario);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Usuario usuario) throws ServicoException {
        try {
            Usuario user = repositorioUsuario.findOne(usuario.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + usuario.getLogin());
            }
            repositorioUsuario.save(usuario);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Usuario user = repositorioUsuario.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + codigo);
            }

            repositorioUsuario.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Usuario buscar(Long codigo) {
        return repositorioUsuario.findOne(codigo);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Usuario validarUsuario(String login, String senha) throws ServicoException {
          Usuario user = repositorioUsuario.findByLogin(login);
          if (user == null){
              throw new ServicoException("Usuario não encontrado");
          }
          if (!user.getSenha().equals(senha)){
              throw new ServicoException ("Usuario ou senha incorretos");
          }
          if (user.getStatus().equals("Inativo")){
              throw new ServicoException ("Usuario está inativo");
          }
          return user;
    }

    @Override
    public Usuario buscarPorLogin(String login) {
       return repositorioUsuario.findByLogin(login);
    }

}
