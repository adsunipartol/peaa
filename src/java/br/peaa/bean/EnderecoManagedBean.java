package br.peaa.bean;

import br.peaa.DAO.EnderecoDAO;
import br.peaa.entidades.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "enderecoMB")
@SessionScoped
public class EnderecoManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EnderecoManagedBean.class);

    private Endereco endereco;
    private List<Endereco> enderecos;
    List<SelectItem> turnoSelecao;
    List<SelectItem> enderecosSelecao;
    private boolean novo;
    private EnderecoDAO enderecodao;

    public EnderecoManagedBean() {
        this.novo = true;
        enderecodao = new EnderecoDAO();
    }

    public static Logger getLogger() {
        return logger;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isNovo() {
        return novo;
    }

    public String novo() {
        novo = true;
        return "form.xhtml";
    }

    public void criar() {
        try {
            enderecodao.iniciarTransacao();
            enderecodao.salvar(endereco);
            enderecodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.enderecos.clear();
            novo = false;
        } catch (Exception ex) {
            enderecodao.desfazTransacao();
            endereco.setCodigo(null);
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    public String editar(Endereco endereco) {
        novo = false;
        this.endereco = endereco;
        return "form.xhtml";
    }

    public void atualizar() {
        try {
            enderecodao.iniciarTransacao();
            enderecodao.atualizar(endereco);
            enderecodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.enderecos.clear();
        } catch (Exception ex) {
            enderecodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Endereco endereco) {
        try {
            enderecodao.iniciarTransacao();
            enderecodao.remover(endereco.getCodigo());
            enderecodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.enderecos.clear();
        } catch (Exception ex) {
            enderecodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Endereco> getEnderecos() {
        this.enderecos = enderecodao.buscarTodos();
        if (this.enderecos == null) {
            this.enderecos = new ArrayList<Endereco>();
        }
        return enderecos;
    }

}
