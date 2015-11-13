package br.peaa.DAO;

import br.peaa.entidades.Perfil;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;

public class PerfilDAO extends DaoGenerico<Perfil>{
    private static final Logger logger = Logger.getLogger(PerfilDAO.class);

    public PerfilDAO() {
        super(Perfil.class);
    }

    @Override
    public void salvar(Perfil perfil) throws ServicoException {
        try {
            super.salvar(perfil);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Perfil perfil) throws ServicoException {
        try {
            Perfil user = super.buscarPeloId(perfil.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado perfil com id " + perfil.getCodigo());
            }
            super.atualizar(perfil);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Perfil user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado perfil com id " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Perfil buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
}
