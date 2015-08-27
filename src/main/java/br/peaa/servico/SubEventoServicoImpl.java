package br.peaa.servico;

import br.peaa.entidades.SubEvento;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.SubEventoRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("subeventoServico")
@Transactional(readOnly = false)
public class SubEventoServicoImpl implements SubEventoServico {

    private static final Logger logger = Logger.getLogger(SubEventoServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private SubEventoRepository repositorioSubEvento;

    @Override
    @Transactional(readOnly = false)
    public void criar(SubEvento subevento) throws ServicoException {
        try {
            repositorioSubEvento.save(subevento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(SubEvento subevento) throws ServicoException {
        try {
            SubEvento user = repositorioSubEvento.findOne(subevento.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado subevento com id " + subevento.getCodigo());
            }
            repositorioSubEvento.save(subevento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            SubEvento user = repositorioSubEvento.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado subevento com id " + codigo);
            }

            repositorioSubEvento.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public SubEvento buscar(Long codigo) {
        return repositorioSubEvento.findOne(codigo);
    }

    @Override
    public List<SubEvento> buscarTodos() {
        return repositorioSubEvento.findAll();
    }

}
