package br.peaa.DAO;

import br.peaa.entidades.Entidade;
import br.peaa.exceptions.ServicoException;
import org.apache.log4j.Logger;

public class EntidadeDAO extends DaoGenerico<Entidade> {

    private static final Logger logger = Logger.getLogger(EntidadeDAO.class);

    public EntidadeDAO() {
        super(Entidade.class);
    }

    @Override
    public void salvar(Entidade entidade) throws ServicoException {
        try {
            super.salvar(entidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Entidade entidade) throws ServicoException {
        try {
            Entidade user = super.buscarPeloId(entidade.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrada entidade com codigo " + entidade.getCodigo());
            }
            super.atualizar(entidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Entidade user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado entidade com codigo " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Entidade buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
}
