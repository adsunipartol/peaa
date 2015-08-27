package br.peaa.servico;

import br.peaa.entidades.Entidade;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.EntidadeRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("entidadeServico")
@Transactional(readOnly = false)
public class EntidadeServicoImpl implements EntidadeServico {

    private static final Logger logger = Logger.getLogger(EntidadeServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private EntidadeRepository repositorioEntidade;

    @Override
    @Transactional(readOnly = false)
    public void criar(Entidade entidade) throws ServicoException {
        try {
            repositorioEntidade.save(entidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Entidade entidade) throws ServicoException {
        try {
            Entidade user = repositorioEntidade.findOne(entidade.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrada entidade com codigo " + entidade.getCodigo());
            }
            repositorioEntidade.save(entidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Entidade user = repositorioEntidade.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com codigo " + codigo);
            }

            repositorioEntidade.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Entidade buscar(Long codigo) {
        return repositorioEntidade.findOne(codigo);
    }

    @Override
    public List<Entidade> buscarTodos() {
        return repositorioEntidade.findAll();
    }

}
