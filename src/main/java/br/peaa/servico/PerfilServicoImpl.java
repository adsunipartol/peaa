package br.peaa.servico;

import br.peaa.entidades.Perfil;
import br.peaa.exceptions.ServicoException;
import br.peaa.repositorio.PerfilRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("perfilServico")
@Transactional(readOnly = false)
public class PerfilServicoImpl implements PerfilServico {

    private static final Logger logger = Logger.getLogger(PerfilServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private PerfilRepository repositorioPerfil;

    @Override
    @Transactional(readOnly = false)
    public void criar(Perfil perfil) throws ServicoException {
        try {
            repositorioPerfil.save(perfil);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizar(Perfil perfil) throws ServicoException {
        try {
            Perfil user = repositorioPerfil.findOne(perfil.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + perfil.getCodigo());
            }
            repositorioPerfil.save(perfil);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void remover(Long codigo) throws ServicoException {
        try {
            Perfil user = repositorioPerfil.findOne(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado usuário com id " + codigo);
            }

            repositorioPerfil.delete(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    @Override
    public Perfil buscar(Long codigo) {
        return repositorioPerfil.findOne(codigo);
    }

    @Override
    public List<Perfil> buscarTodos() {
        return repositorioPerfil.findAll();
    }

}
