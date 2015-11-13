package br.peaa.DAO;

import br.peaa.entidades.Evento;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;

public class EventoDAO extends DaoGenerico<Evento> {

    private static final Logger logger = Logger.getLogger(EventoDAO.class);

    public EventoDAO() {
        super(Evento.class);
    }

    @Override
    public void salvar(Evento evento) throws ServicoException {
        try {
            super.salvar(evento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }

    @Override
    public void atualizar(Evento evento) throws ServicoException {
        try {
            Evento user = super.buscarPeloId(evento.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado evento com id " + evento.getCodigo());
            }
            super.atualizar(evento);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public void remover(Long codigo) throws ServicoException {
        try {
            Evento user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado evento com id " + codigo);
            }
            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }

    public Evento buscar(Long codigo) {
        return super.buscarPeloId(codigo);
    }
    
    public List<Evento> buscarPorNome(String nome) {

        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Evento e where e.nome = :nome");
        qr.setParameter("nome", nome);

        return (List<Evento>) qr.list();
    }

    public List<Evento> buscarPorCaractere(String nome) {
        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Evento e where upper(e.nome) like upper(:nome)");
        qr.setParameter("nome", "%" + nome + "%");

        return (List<Evento>) qr.list();
    }
}
