package br.peaa.bean;

import br.peaa.entidades.Perfil;
import br.peaa.servico.PerfilServico;
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

@ManagedBean(name = "perfilMB")
@SessionScoped
public class PerfilManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PerfilManagedBean.class);

    @ManagedProperty(value = "#{perfilServico}")
    private PerfilServico srvPerfis;
    private Perfil perfil;
    private List<Perfil> perfis;
    private List<SelectItem> perfisSelecao;
    private boolean novo;

    public PerfilManagedBean() {
        this.novo = true;
        perfil = new Perfil();
    }

    public void setSrvPerfis(PerfilServico srvPerfis) {
        this.srvPerfis = srvPerfis;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isNovo() {
        return novo;
    }

    public String novo() {
        novo = true;
        perfil = new Perfil();
        return "form.xhtml";
    }

    public void criar() {
        try {
            srvPerfis.criar(perfil);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.perfis.clear();
            novo = false;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public String editar(Perfil perfil) {
        novo = false;
        this.perfil = perfil;
        return "form.xhtml";
    }

    public void atualizar() {
        try {
            srvPerfis.atualizar(perfil);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.perfis.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Perfil perfil) {
        try {
            srvPerfis.remover(perfil.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.perfis.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Perfil> getPerfis() {
        if ((this.perfis == null || this.perfis.isEmpty()) && srvPerfis != null) {
            this.perfis = srvPerfis.buscarTodos();
        }
        if (this.perfis == null) {
            this.perfis = new ArrayList<Perfil>();
        }

        return perfis;
    }

    public List<SelectItem> getPerfisSelecao() {
        if (perfisSelecao == null) {
            perfisSelecao = new ArrayList<SelectItem>();
            for (Perfil p : srvPerfis.buscarTodos()) {
                perfisSelecao.add(new SelectItem(p.getCodigo(), p.getNome()));
            }
        }
        return perfisSelecao;
    }

    public void setPerfisSelecao(List<SelectItem> perfisSelecao) {
        this.perfisSelecao = perfisSelecao;
    }

}
