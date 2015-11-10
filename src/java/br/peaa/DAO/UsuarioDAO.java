package br.peaa.DAO;

import br.peaa.entidades.Usuario;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;

public class UsuarioDAO extends DaoGenerico<Usuario> {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class);

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public void criar(Usuario usuario) throws ServicoException {
        try {
            if (buscarPorLogin(usuario.getLogin()) != null) {
                throw new Exception("Já existe usuario com login " + usuario.getLogin());
            }
            super.salvar(usuario);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Usuario usuario) throws ServicoException {
        try {
            Usuario user = super.buscarPeloId(usuario.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + usuario.getLogin());
            }
            super.atualizar(usuario);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Usuario user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Usuario buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return super.buscarTodos();
    }

    public Usuario validarUsuario(String login, String senha) throws ServicoException {
        Usuario user = buscarPorLogin(login);
        if (user == null) {
            throw new ServicoException("Usuario não encontrado");
        }
        if (!user.getSenha().equals(senha)) {
            throw new ServicoException("Usuario ou senha incorretos");
        }
        if (user.getStatus().equals("Inativo")) {
            throw new ServicoException("Usuario está inativo");
        }
        return user;
    }

    public Usuario buscarPorLogin(String login) throws ServicoException{
        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Usuario u where u.login = :login");
        qr.setParameter("login", login);
        
        return (Usuario) qr.uniqueResult();
    }
}
