package br.peaa.servico;

import br.peaa.entidades.Estado;
import br.peaa.repositorio.EstadoRepository;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("estadoServico")
@Transactional(readOnly = false)
public class EstadoServicoImpl implements EstadoServico {

    private static final Logger logger = Logger.getLogger(EstadoServicoImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private EstadoRepository repositorioEstado;

   
    @Override
    public Estado buscar(Long codigo) {
        return repositorioEstado.findOne(codigo);
    }

    @Override
    public List<Estado> buscarTodos() {
        return repositorioEstado.findAll();
    }

}
