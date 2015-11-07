package br.peaa.DAO;

import br.peaa.entidades.Pessoa;
import br.peaa.exceptions.ServicoException;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;

public class PessoaDAO extends DaoGenerico<Pessoa>{

    private static final Logger logger = Logger.getLogger(PessoaDAO.class);
    
    public PessoaDAO() {
        super(Pessoa.class);
    }
    
    @Override
    public void salvar(Pessoa pessoa) throws ServicoException {
        try {
            super.salvar(pessoa);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            throw new ServicoException(ex);
        }
    }
    
    @Override
    public void atualizar(Pessoa pessoa) throws ServicoException {
        try {
            Pessoa user = super.buscarPeloId(pessoa.getCodigo());
            if (user == null) {
                throw new Exception("Não foi encontrado pessoa com código " + pessoa.getCodigo());
            }
            super.atualizar(pessoa);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }
    
    public void remover(Long codigo) throws ServicoException {
        try {
            Pessoa user = super.buscarPeloId(codigo);
            if (user == null) {
                throw new Exception("Não foi encontrado pessoa com codigo " + codigo);
            }

            super.remover(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ServicoException(ex);
        }
    }
    
    public Pessoa buscarPeloId(Long codigo){
        return super.buscarPeloId(codigo);
    }

    public Pessoa buscarPorRa(String ra) throws ServicoException{
        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Pessoa p where p.ra = :ra");
        qr.setParameter("ra", ra);
        
        return (Pessoa) qr.uniqueResult();
    }

    public List<Pessoa> buscarPessoaPorEvento(Long codEvento) throws ServicoException{
        Query qr = HibernateUtil.getInstance().obterSessao().
                createQuery(" from Pessoa p inner join p.eventos e where e.codigo = :codigo");
        qr.setParameter("codigo", codEvento);
        
        return (List<Pessoa>) qr.list();
    }
}
