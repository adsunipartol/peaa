package br.peaa.servico;

import br.peaa.entidades.Curso;
import br.peaa.entidades.Turma;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.CursoRepository;
import br.peaa.repositorio.TurmaRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("turmaServico")
@Transactional(readOnly = false)
public class TurmaServicoImpl implements TurmaServico {

    private static final Logger logger = Logger.getLogger(TurmaServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public CursoRepository repositorioCurso;
    
    @Autowired
    private TurmaRepository repositorioTurma;

    @Override
    @Transactional(readOnly = false)
    public void criar(Turma turma) throws ServicoException {
        try {
            repositorioTurma.save(turma);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Turma turma) throws ServicoException {
        try {
            Turma t = repositorioTurma.findOne(turma.getCodigo());
            if (t == null) {
                throw new Exception("Não foi encontrado usuário com id " + turma.getCodigo());
            }
            repositorioTurma.save(turma);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Turma user = repositorioTurma.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + codigo);
            }

            repositorioTurma.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Turma buscar(Long codigo) {
        return repositorioTurma.findOne(codigo);
    }

    @Override
    public List<Turma> buscarTodos() {
        return repositorioTurma.findAll();
    }

    @Override
    public List<Turma> buscarTurmaPorCurso(Long cod_curso) {
        Curso c = repositorioCurso.findOne(cod_curso);
        return repositorioTurma.findTurmaByCurso(c);
    }

}
