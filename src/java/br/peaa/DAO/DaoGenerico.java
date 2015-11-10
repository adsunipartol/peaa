package br.peaa.DAO;

import br.peaa.exceptions.ServicoException;
import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;

public abstract class DaoGenerico<T> {

    private Class clazz;

    public DaoGenerico(Class clazz) {
        this.clazz = clazz;
    }

    public void salvar(T registro) throws ServicoException {
        Session sessao = HibernateUtil.getInstance().obterSessao();
        sessao.save(registro);
    }

    public void atualizar(T registro) throws ServicoException {
        Session sessao = HibernateUtil.getInstance().obterSessao();
        sessao.update(registro);
    }

    public void remover(T registro) throws ServicoException {
        Session session = HibernateUtil.getInstance().obterSessao();
        session.delete(registro);
    }

    public void remover(Serializable pk) throws ServicoException {
        T registro = buscarPeloId(pk);
        remover(registro);
    }

    public T buscarPeloId(Serializable pk) {
        Session session = HibernateUtil.getInstance().obterSessao();
        return (T) session.get(this.clazz, pk);
    }

    public java.util.List<T> buscarTodos() {
        Session session = HibernateUtil.getInstance().obterSessao();
        Query qr = session.createQuery(" from " + clazz.getName());
        return qr.list();
    }

    public void iniciarTransacao() {
        HibernateUtil.getInstance().obterSessao().beginTransaction();
    }

    public void confirmaTransacao() {
        HibernateUtil.getInstance().obterSessao().getTransaction().commit();
        HibernateUtil.getInstance().finalizarSessao();
    }

    public void desfazTransacao() {
        HibernateUtil.getInstance().obterSessao().getTransaction().rollback();
        HibernateUtil.getInstance().finalizarSessao();
    }
}
