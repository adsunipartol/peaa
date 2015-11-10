package br.peaa.DAO;

import br.peaa.entidades.Evento;
import br.peaa.exceptions.ServicoException;
import org.apache.log4j.Logger;

public class EventoDAO extends DaoGenerico<Evento> {

    private static final Logger logger = Logger.getLogger(EventoDAO.class);

    public EventoDAO() {
        super(Evento.class);
    }

    @Override
    public void salvar(Evento evento) throws ServicoException {
        try {
            super.salvar(evento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Evento evento) throws ServicoException {
        try {
            Evento user = super.buscarPeloId(evento.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado evento com id " + evento.getCodigo());
            }
            super.salvar(evento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Evento user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado evento com id " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Evento buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
}
