package br.peaa.bean;

import br.peaa.DAO.TurmaDAO;
import br.peaa.entidades.Turma;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "turmaMB")
@SessionScoped
public class TurmaManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(TurmaManagedBean.class);

    private Turma turma;
    private List<Turma> turmas;
    private boolean novo;
    private TurmaDAO turmadao;

    public TurmaManagedBean() {
        this.novo = true;
        turma = new Turma();
        turmadao = new TurmaDAO();
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
            turmadao.iniciarTransacao();
            turmadao.salvar(turma);
            turmadao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
        } catch (Exception ex) {
            turmadao.desfazTransacao();
            turma.setCodigo(null);
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
            turmadao.iniciarTransacao();
            turmadao.atualizar(turma);
            turmadao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.turmas.clear();
        } catch (Exception ex) {
            turmadao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    } 

    public void remover(Turma turma) {
        try {
            turmadao.iniciarTransacao();
            turmadao.remover(turma.getCodigo());
            turmadao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.turmas.clear();
        } catch (Exception ex) {
            turmadao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Turma> getTurmas() {
        this.turmas = turmadao.buscarTodos();
        if (this.turmas == null) {
            this.turmas = new ArrayList<Turma>();
        }
        return turmas;
    }
   
}
