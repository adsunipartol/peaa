package br.peaa.servico;

import br.peaa.entidades.Pessoa;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.PessoaRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("pessoaServico")
@Transactional(readOnly = false)
public class PessoaServicoImpl implements PessoaServico {

    private static final Logger logger = Logger.getLogger(PessoaServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private PessoaRepository repositorioPessoa;

    @Override
    @Transactional(readOnly = false)
    public void criar(Pessoa pessoa) throws ServicoException {
        try {
            repositorioPessoa.save(pessoa);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Pessoa pessoa) throws ServicoException {
        try {
            Pessoa user = repositorioPessoa.findOne(pessoa.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado pessoa com codigo " + pessoa.getCodigo());
            }
            repositorioPessoa.save(pessoa);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Pessoa user = repositorioPessoa.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado pessoa com codigo " + codigo);
            }

            repositorioPessoa.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Pessoa buscar(Long codigo) {
        return repositorioPessoa.findOne(codigo);
    }

    @Override
    public List<Pessoa> buscarTodos() {
        return repositorioPessoa.findAll();
    }

    @Override
    public Pessoa buscarPorRa(String ra) {
        return repositorioPessoa.findPessoaByRa(ra);
    }

    @Override
    public List<Pessoa> buscarPessoaPorEvento(Long codEvento) {
        return repositorioPessoa.ParticipacaoEventos(codEvento);
    }

}
