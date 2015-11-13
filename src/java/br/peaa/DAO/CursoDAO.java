package br.peaa.DAO;

import br.peaa.entidades.Curso;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;

public class CursoDAO extends DaoGenerico<Curso> {

    private static final Logger logger = Logger.getLogger(CursoDAO.class);

    public CursoDAO() {
        super(Curso.class);
    }

    @Override
    public void salvar(Curso curso) throws ServicoException {
        try {
            super.salvar(curso);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Curso curso) throws ServicoException {
        try {
            Curso user = super.buscarPeloId(curso.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado curso com Codigo " + curso.getCodigo());
            }
            super.atualizar(curso);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long Codigo) throws ServicoException {
        try {
            Curso curso = super.buscarPeloId(Codigo);
            if (curso == null) {
                throw new Exception("Não foi encontrado curso com Codigo " + Codigo);
            }
            super.remover(curso);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Curso buscar(Long Codigo) {
        return super.buscarPeloId(Codigo);
    }

    public List<Curso> buscarPorNome(String nome) {

        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Curso c where c.nome = :nome");
        qr.setParameter("nome", nome);

        return (List<Curso>) qr.list();
    }

    public List<Curso> buscarPorCaractere(String nome) {
        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Curso c where upper(c.nome) like upper(:nome)");
        qr.setParameter("nome", "%" + nome + "%");

        return (List<Curso>) qr.list();
    }
    
    public List<Curso> listarCursos() {
        return super.buscarTodos();
    }
}
