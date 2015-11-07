package br.peaa.DAO;

import br.peaa.entidades.SubEvento;
import br.peaa.exceptions.ServicoException;
import org.apache.log4j.Logger;

public class SubEventoDAO extends DaoGenerico<SubEvento>{
    private static final Logger logger = Logger.getLogger(SubEventoDAO.class);

    public SubEventoDAO() {
        super(SubEvento.class);
    }

    @Override
    public void salvar(SubEvento subevento) throws ServicoException {
        try {
            super.salvar(subevento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(SubEvento subevento) throws ServicoException {
        try {
            SubEvento user = super.buscarPeloId(subevento.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado subevento com id " + subevento.getCodigo());
            }
            super.atualizar(subevento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            SubEvento user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado subevento com id " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public SubEvento buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
}
