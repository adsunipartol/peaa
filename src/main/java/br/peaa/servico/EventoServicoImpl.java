package br.peaa.servico;

import br.peaa.entidades.Evento;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.EventoRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("eventoServico")
@Transactional(readOnly = false)
public class EventoServicoImpl implements EventoServico {

    private static final Logger logger = Logger.getLogger(EventoServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private EventoRepository repositorioEvento;

    @Override
    @Transactional(readOnly = false)
    public void criar(Evento evento) throws ServicoException {
        try {
            repositorioEvento.save(evento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }
    @Override
    @Transactional(readOnly = false)
    public void atualizar(Evento evento) throws ServicoException {
        try {
            Evento user = repositorioEvento.findOne(evento.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado evento com id " + evento.getCodigo());
            }
            repositorioEvento.save(evento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Evento user = repositorioEvento.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado evento com id " + codigo);
            }

            repositorioEvento.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Evento buscar(Long codigo) {
        return repositorioEvento.findOne(codigo);
    }

    @Override
    public List<Evento> buscarTodos() {
        return repositorioEvento.findAll();
    }

}
