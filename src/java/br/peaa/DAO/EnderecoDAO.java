package br.peaa.DAO;

import br.peaa.entidades.Endereco;
import br.peaa.exceptions.ServicoException;
import org.apache.log4j.Logger;

public class EnderecoDAO extends DaoGenerico<Endereco> {

    private static final Logger logger = Logger.getLogger(EnderecoDAO.class);

    public EnderecoDAO() {
        super(Endereco.class);
    }

    @Override
    public void salvar(Endereco endereco) throws ServicoException {
        try {
            if (super.buscarPeloId(endereco.getCodigo()) != null) {
                throw new Exception("Já existe um endereço com codigo " + endereco.getCodigo());
            }
            super.salvar(endereco);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Endereco endereco) throws ServicoException {
        try {
            Endereco user = super.buscarPeloId(endereco.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado endereço com codigo " + endereco.getCodigo());
            }
            super.salvar(endereco);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Endereco user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado endereço com codigo " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Endereco buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
}
