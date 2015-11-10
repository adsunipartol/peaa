package br.peaa.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
    private static HibernateUtil instance;
    
    public static HibernateUtil getInstance(){
        if(instance == null){
            instance = new HibernateUtil();
        }
        return instance;
    }
    
    private SessionFactory factory;
    private Session session;
    
    private HibernateUtil(){
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }
    
    public Session obterSessao(){
        if(session == null){
            session = factory.openSession();
        }
        return session;
    }
    
    public void finalizarSessao(){
        session = null;
    }
}
