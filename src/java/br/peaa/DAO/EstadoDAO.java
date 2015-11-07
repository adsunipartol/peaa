package br.peaa.DAO;

import br.peaa.entidades.Estado;
import org.apache.log4j.Logger;

public class EstadoDAO extends DaoGenerico<Estado> {

    private static final Logger logger = Logger.getLogger(EstadoDAO.class);

    public EstadoDAO() {
        super(Estado.class);
    }

    public Estado buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
}
