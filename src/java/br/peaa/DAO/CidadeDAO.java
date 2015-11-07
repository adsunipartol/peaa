package br.peaa.DAO;

import br.peaa.entidades.Cidade;
import br.peaa.entidades.Estado;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;

public class CidadeDAO extends DaoGenerico<Cidade> {

    public CidadeDAO() {
        super(Cidade.class);
    }

    private static final Logger logger = Logger.getLogger(CidadeDAO.class);

    @Override
    public void salvar(Cidade cidade) throws ServicoException {
        try {
            if (super.buscarPeloId(cidade.getCodigo()) != null) {
                throw new Exception("Já existe uma cidade com o codigo " + cidade.getCodigo());
            }
            super.salvar(cidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Cidade cidade) throws ServicoException {
        try {
            Cidade user = super.buscarPeloId(cidade.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado cidade com Codigo " + cidade.getCodigo());
            }
            super.salvar(cidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long Codigo) throws ServicoException {
        try {
            Cidade cidade = super.buscarPeloId(Codigo);
            if (cidade == null) {
                throw new Exception("Não foi encontrado cidade com Codigo " + Codigo);
            }

            super.remover(cidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public List<Cidade> buscarCidadePorEstado(Long cod_estado) {
        EstadoDAO estadodao = new EstadoDAO();
        Estado est = estadodao.buscar(cod_estado);
        return est.getCidades();
    }
}
