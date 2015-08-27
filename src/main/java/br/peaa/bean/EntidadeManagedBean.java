package br.peaa.bean;

import br.peaa.entidades.Cidade;
import br.peaa.entidades.Endereco;
import br.peaa.entidades.Entidade;
import br.peaa.servico.EntidadeServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "entidadeMB")
@SessionScoped
public class EntidadeManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EntidadeManagedBean.class);

    @ManagedProperty(value = "#{entidadeServico}")
    private EntidadeServico srvEntidades;
    private Entidade entidade;
    private Endereco endereco;
    private List<Entidade> entidades;

    public EntidadeManagedBean() {

    }

    public void setSrvEntidades(EntidadeServico srvEntidades) {
        this.srvEntidades = srvEntidades;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public String novo() {
        entidade = new Entidade();
        endereco = new Endereco();
        endereco.setCidade(new Cidade());
        return "cadastroentidade.xhtml";
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void criar() {
        try {
            if (entidade.getCodigo() == null) {
                entidade.setEndereco(endereco);
                srvEntidades.criar(entidade);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            } else {
                atualizar();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public String editar(Entidade entidade) {
        this.entidade = entidade;
        return "cadastroentidade.xhtml";
    }

    public void atualizar() {
        try {
            srvEntidades.atualizar(entidade);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.entidades.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Entidade entidade) {
        try {
            srvEntidades.remover(entidade.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.entidades.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Entidade> getEntidades() {
        if ((this.entidades == null || this.entidades.isEmpty()) && srvEntidades != null) {
            this.entidades = srvEntidades.buscarTodos();
        }
        if (this.entidades == null) {
            this.entidades = new ArrayList<Entidade>();
        }

        return entidades;
    }

}
