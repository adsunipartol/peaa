package br.peaa.servico;

import br.peaa.entidades.Endereco;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.EnderecoRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("enderecoServico")
@Transactional(readOnly = false)
public class EnderecoServicoImpl implements EnderecoServico {

    private static final Logger logger = Logger.getLogger(EnderecoServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private EnderecoRepository repositorioEndereco;

    @Override
    @Transactional(readOnly = false)
    public void criar(Endereco endereco) throws ServicoException {
        try {
            if (repositorioEndereco.findOne(endereco.getCodigo()) != null) {
                throw new Exception("Já existe um usuário com codigo " + endereco.getCodigo());
            }

            repositorioEndereco.save(endereco);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Endereco endereco) throws ServicoException {
        try {
            Endereco user = repositorioEndereco.findOne(endereco.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com codigo " + endereco.getCodigo());
            }
            repositorioEndereco.save(endereco);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Endereco user = repositorioEndereco.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com codigo " + codigo);
            }

            repositorioEndereco.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Endereco buscar(Long codigo) {
        return repositorioEndereco.findOne(codigo);
    }

    @Override
    public List<Endereco> buscarTodos() {
        return repositorioEndereco.findAll();
    }

}
