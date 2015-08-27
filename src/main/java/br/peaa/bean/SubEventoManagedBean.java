package br.peaa.bean;

import br.peaa.entidades.Evento;
import br.peaa.entidades.SubEvento;
import br.peaa.servico.SubEventoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "subeventoMB")
@SessionScoped
public class SubEventoManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(SubEventoManagedBean.class);

    @ManagedProperty(value = "#{subeventoServico}")
    private SubEventoServico srvSubEventos;
    private SubEvento subevento;
    private List<SubEvento> subeventos;
    private List<SelectItem> subeventosSelecao;
    private boolean novo;
    private Evento eventoCurso;

    public SubEventoManagedBean() {
        this.novo = true;
        subevento = new SubEvento();
    }

    public Evento getEventoCurso() {
        return eventoCurso;
    }

    public void setEventoCurso(Evento eventoCurso) {
        this.eventoCurso = eventoCurso;
    }

    public void setSrvSubEventos(SubEventoServico srvSubEventos) {
        this.srvSubEventos = srvSubEventos;
    }

    public SubEvento getSubEvento() {
        return subevento;
    }

    public void setSubEvento(SubEvento subevento) {
        this.subevento = subevento;
    }

    public boolean isNovo() {
        return novo;
    }

    public String novoSubEvento() {
        novo = true;
        eventoCurso = new Evento();
        subevento = new SubEvento();
        return "cadsubevento.xhtml";
    }

    public void criar() {
        try {
            subevento.setEvento(eventoCurso);
            srvSubEventos.criar(subevento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public String editar(SubEvento subevento) {
        novo = false;
        this.subevento = subevento;
        return "cadsubevento.xhtml";
    }

    public void atualizar() {
        try {
            srvSubEventos.atualizar(subevento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.subeventos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(SubEvento subevento) {
        try {
            srvSubEventos.remover(subevento.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.subeventos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<SubEvento> getSubEventos() {
        if ((this.subeventos == null || this.subeventos.isEmpty()) && srvSubEventos != null) {
            this.subeventos = srvSubEventos.buscarTodos();
        }
        if (this.subeventos == null) {
            this.subeventos = new ArrayList<SubEvento>();
        }

        return subeventos;
    }

    public List<SelectItem> getSubEventosSelecao() {
        if (subeventosSelecao == null) {
            subeventosSelecao = new ArrayList<SelectItem>();
            for (SubEvento c : srvSubEventos.buscarTodos()) {
                subeventosSelecao.add(new SelectItem(c, c.getNome()));
            }
        }
        return subeventosSelecao;

    }
}
