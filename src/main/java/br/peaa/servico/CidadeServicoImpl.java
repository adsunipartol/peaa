package br.peaa.servico;

import br.peaa.entidades.Cidade;
import br.peaa.entidades.Estado;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.CidadeRepository;
import br.peaa.repositorio.EstadoRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cidadeServico")
@Transactional(readOnly = false)
public class CidadeServicoImpl implements CidadeServico {

    private static final Logger logger = Logger.getLogger(CidadeServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public CidadeRepository repositorioCidade;
    
    @Autowired
    public EstadoRepository repositorioEstado;

    @Override
    @Transactional(readOnly = false)
    public void criar(Cidade cidade) throws ServicoException {
        try {
            if (repositorioCidade.findOne(cidade.getCodigo()) != null) {
                throw new Exception("Já existe uma cidade com o codigo " + cidade.getCodigo());
            }
            repositorioCidade.save(cidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Cidade cidade) throws ServicoException {
        try {
            Cidade user = repositorioCidade.findOne(cidade.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado cidade com Codigo " + cidade.getCodigo());
            }
            repositorioCidade.save(cidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long Codigo) throws ServicoException {
        try {
            Cidade cidade = repositorioCidade.findOne(Codigo);
            if (cidade == null) {
                throw new Exception("Não foi encontrado cidade com Codigo " + Codigo);
            }

            repositorioCidade.delete(cidade);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Cidade buscar(Long Codigo) {
        return repositorioCidade.findOne(Codigo);
    }

    @Override
    public List<Cidade> buscarTodos() {
        return repositorioCidade.findAll();
    }

    @Override
    public List<Cidade> buscarCidadePorEstado(Long cod_estado) {
        Estado est = repositorioEstado.findOne(cod_estado);
        return repositorioCidade.findCidadeByEstado(est);
    }

}
