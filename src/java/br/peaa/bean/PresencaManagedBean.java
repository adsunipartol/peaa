package br.peaa.bean;

import br.peaa.DAO.EventoDAO;
import br.peaa.entidades.Evento;
import br.peaa.entidades.Pessoa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "presencaMB")
@SessionScoped
public class PresencaManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UsuarioManagedBean.class);

    private List<Pessoa> pessoaParticipante;
    private Evento evt = new Evento();

    public PresencaManagedBean() {
    }

    public void registrar() {
        try {
            EventoDAO eventodao = new EventoDAO();
            evt = eventodao.buscar(Long.MIN_VALUE);
            if (evt.getPessoas() == null) {
                evt.setPessoas(new ArrayList<Pessoa>());
            }
            evt.setPessoas(pessoaParticipante);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " " + pessoaParticipante.toString(), null));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

}
