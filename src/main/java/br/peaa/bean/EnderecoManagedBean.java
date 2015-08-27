package br.peaa.bean;

import br.peaa.entidades.Endereco;
import br.peaa.servico.EnderecoServico;
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

    @ManagedProperty(value = "#{enderecoServico}")
    private EnderecoServico srvEnderecos;
    private Endereco endereco;
    private List<Endereco> enderecos;
    List<SelectItem> turnoSelecao;
    List<SelectItem> enderecosSelecao;
    private boolean novo;

    public EnderecoManagedBean() {
        this.novo = true;
    }

    public void setSrvEnderecos(EnderecoServico srvEnderecos) {
        this.srvEnderecos = srvEnderecos;
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

    public EnderecoServico getSrvEnderecos() {
        return srvEnderecos;
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
            srvEnderecos.criar(endereco);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.enderecos.clear();
            novo = false;
        } catch (Exception ex) {
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
            srvEnderecos.atualizar(endereco);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.enderecos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Endereco endereco) {
        try {
            srvEnderecos.remover(endereco.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.enderecos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Endereco> getEnderecos() {
        if ((this.enderecos == null || this.enderecos.isEmpty()) && srvEnderecos != null) {
            this.enderecos = srvEnderecos.buscarTodos();
        }
        if (this.enderecos == null) {
            this.enderecos = new ArrayList<Endereco>();
        }
        return enderecos;
    }


}
