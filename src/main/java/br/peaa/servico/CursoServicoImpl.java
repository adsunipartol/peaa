package br.peaa.servico;

import br.peaa.entidades.Curso;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.CursoRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cursoServico")
@Transactional(readOnly = false)
public class CursoServicoImpl implements CursoServico {

    private static final Logger logger = Logger.getLogger(CursoServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private CursoRepository repositorioCurso;

    @Override
    @Transactional(readOnly = false)
    public void criar(Curso curso) throws ServicoException {
        try {
            repositorioCurso.save(curso);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Curso curso) throws ServicoException {
        try {
            Curso user = repositorioCurso.findOne(curso.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado curso com Codigo " + curso.getCodigo());
            }
            repositorioCurso.save(curso);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long Codigo) throws ServicoException {
        try {
            Curso curso = repositorioCurso.findOne(Codigo);
            if (curso == null) {
                throw new Exception("Não foi encontrado curso com Codigo " + Codigo);
            }
            repositorioCurso.delete(curso);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Curso buscar(Long Codigo) {
        return repositorioCurso.findOne(Codigo);
    }

    @Override
    public List<Curso> buscarTodos() {
        return repositorioCurso.findAll();
    }

    @Override
    public Curso buscarPorNome(String nome) {
        return repositorioCurso.findCursoByNome(nome);
    }

    @Override
    public List<Curso> buscarPorCaractere(String parametro) {
        return repositorioCurso.findCursoByNomeLike(parametro);
    }
    
    

}
