package br.peaa.bean;

import br.peaa.entidades.Turma;
import br.peaa.repositorio.TurmaRepository;
import br.peaa.servico.TurmaServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "turmaMB")
@SessionScoped
public class TurmaManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(TurmaManagedBean.class);

    @ManagedProperty(value = "#{turmaServico}")
    private TurmaServico srvTurmas;
    private Turma turma;
    private List<Turma> turmas;
    private TurmaRepository repTurma;
    private boolean novo;

    public TurmaManagedBean() {
        this.novo = true;
        turma = new Turma();
    }
    
    public void setSrvTurmas(TurmaServico srvTurmas) {
        this.srvTurmas = srvTurmas;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public boolean isNovo() {
        return novo;
    }
   
    public void criar() {
        try {
            srvTurmas.criar(turma);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public String editar(Turma turma) {
        novo = false;
        this.turma = turma;
        return "viewturma.xhtml";
    }

    public void atualizar() {
        try {
            srvTurmas.atualizar(turma);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.turmas.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    } 

    public void remover(Turma turma) {
        try {
            srvTurmas.remover(turma.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.turmas.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Turma> getTurmas() {
        if ((this.turmas == null || this.turmas.isEmpty()) && srvTurmas != null) {
            this.turmas = srvTurmas.buscarTodos();
        }
        if (this.turmas == null) {
            this.turmas = new ArrayList<Turma>();
        }
        return turmas;
    }
   
}
