package br.peaa.bean;

import br.peaa.DAO.PerfilDAO;
import br.peaa.entidades.Perfil;
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

    private Perfil perfil;
    private List<Perfil> perfis;
    private List<SelectItem> perfisSelecao;
    private boolean novo;
    private PerfilDAO perfildao;

    public PerfilManagedBean() {
        this.novo = true;
        perfil = new Perfil();
        perfildao = new PerfilDAO();
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
            perfildao.iniciarTransacao();
            perfildao.salvar(perfil);
            perfildao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.perfis.clear();
            novo = false;
        } catch (Exception ex) {
            perfildao.desfazTransacao();
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
            perfildao.iniciarTransacao();
            perfildao.atualizar(perfil);
            perfildao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.perfis.clear();
        } catch (Exception ex) {
            perfildao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Perfil perfil) {
        try {
            perfildao.iniciarTransacao();
            perfildao.remover(perfil.getCodigo());
            perfildao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.perfis.clear();
        } catch (Exception ex) {
            perfildao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Perfil> getPerfis() {
        this.perfis = perfildao.buscarTodos();
        if (this.perfis == null) {
            this.perfis = new ArrayList<Perfil>();
        }
        return perfis;
    }

    public List<SelectItem> getPerfisSelecao() {
        if (perfisSelecao == null) {
            perfisSelecao = new ArrayList<SelectItem>();
            for (Perfil p : perfildao.buscarTodos()) {
                perfisSelecao.add(new SelectItem(p.getCodigo(), p.getNome()));
            }
        }
        return perfisSelecao;
    }

    public void setPerfisSelecao(List<SelectItem> perfisSelecao) {
        this.perfisSelecao = perfisSelecao;
    }

}
