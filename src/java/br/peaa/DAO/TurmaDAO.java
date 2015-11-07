package br.peaa.DAO;

import br.peaa.entidades.Curso;
import br.peaa.entidades.Turma;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;

public class TurmaDAO extends DaoGenerico<Turma>{
    private static final Logger logger = Logger.getLogger(TurmaDAO.class);

    public TurmaDAO() {
        super(Turma.class);
    }

    @Override
    public void salvar(Turma turma) throws ServicoException {
        try {
            super.salvar(turma);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Turma turma) throws ServicoException {
        try {
            Turma t = super.buscarPeloId(turma.getCodigo());
            if (t == null) {
                throw new Exception("Não foi encontrado usuário com id " + turma.getCodigo());
            }
            super.atualizar(turma);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Turma user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Turma buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }

    public List<Turma> buscarTodos() {
        return super.buscarTodos();
    }

    public List<Turma> buscarTurmaPorCurso(Long cod_curso) {
        CursoDAO cursodao = new CursoDAO();
        Curso c = cursodao.buscarPeloId(cod_curso);
        
        return c.getTurmas();
    }
}