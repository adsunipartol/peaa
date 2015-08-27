package br.peaa.bean;

import br.peaa.entidades.Evento;
import br.peaa.entidades.Pessoa;
import br.peaa.servico.EventoServico;
import br.peaa.servico.PresencaServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "presencaMB")
@SessionScoped
public class PresencaManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UsuarioManagedBean.class);

    @ManagedProperty(value = "#{presencaServico}")
    private PresencaServico srvPresencas;
    private List<Pessoa> pessoaParticipante;
    @ManagedProperty(value = "#{eventoServico}")
    private EventoServico srvEvento;
    private Evento evt = new Evento();

    public PresencaManagedBean() {
    }

    public void registrar() {
        try {
            evt = srvEvento.buscar(Long.MIN_VALUE);
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
