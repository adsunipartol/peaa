package br.peaa.bean;

import br.peaa.DAO.EntidadeDAO;
import br.peaa.DAO.EstadoDAO;
import br.peaa.entidades.Cidade;
import br.peaa.entidades.Endereco;
import br.peaa.entidades.Entidade;
import br.peaa.entidades.Estado;
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

    private Entidade entidade;
    private Endereco endereco;
    private Estado estado;
    private Cidade cidade;
    private List<Entidade> entidades;
    private List<Entidade> entidadesSelecao;
    private EntidadeDAO entidadedao;
    private EstadoDAO estadodao;
    private String paramBusca;

    @ManagedProperty(value = "#{RegiaoMB}")
    private RegiaoManagedBean regiaoMB;

    public EntidadeManagedBean() {
        entidade = new Entidade();
        endereco = new Endereco();
        estado = new Estado();
        cidade = new Cidade();
        entidades = new ArrayList<Entidade>();
        entidadedao = new EntidadeDAO();
        estadodao = new EstadoDAO();
        paramBusca = "";
    }

    public String getParamBusca() {
        return paramBusca;
    }

    public void setParamBusca(String paramBusca) {
        this.paramBusca = paramBusca;
    }

    public Entidade getEntidade() {
        //caso o usuário insira manualmente a url, a entidade vai estar null, 
        //então deve-se instanciar as classes como se ele estivesse querendo cadastrar uma nova entidade
        if (entidade == null) {
            novo();
        }
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public List<Entidade> getEntidadesSelecao() {
        return entidadesSelecao = (List<Entidade>) entidadedao.buscarTodos();
    }

    public void setEntidadesSelecao(List<Entidade> entidadesSelecao) {
        this.entidadesSelecao = entidadesSelecao;
    }

    public String novo() {
        paramBusca = "";
        entidade = new Entidade();
        endereco = new Endereco();
        regiaoMB.setEstado(new Estado());
        regiaoMB.setCidade(new Cidade());
        return "cadastroentidade.xhtml";
    }
    
    public String editar(Entidade entidade) {
        paramBusca = "";
        this.entidade = entidade;
        this.endereco = entidade.getEndereco();
        this.regiaoMB.setEstado(entidade.getEndereco().getCidade().getEstado());
        this.regiaoMB.setCidades(null);
        this.regiaoMB.getCidades();
        this.regiaoMB.setCidade(entidade.getEndereco().getCidade());
        return "cadastroentidade.xhtml";
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void criar() {
        if (entidade.getCodigo() == null) {
            try {
                endereco.setCidade(regiaoMB.getCidade());
                entidade.setEndereco(endereco);
                entidadedao.iniciarTransacao();
                entidadedao.salvar(entidade);
                entidadedao.confirmaTransacao();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            } catch (Exception ex) {
                //faz o rollback e fecha a sessão para que seja aberta uma tentativa posterior
                entidadedao.desfazTransacao();
                //seta os códigos pra null para ao tentar salvar novamente não caia no atualizar()
                entidade.setCodigo(null);
                endereco.setCodigo(null);
                logger.error(ex.getMessage(), ex);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            }
        } else {
            atualizar();
        }
    }

    public void atualizar() {
        try {
            endereco.setCidade(regiaoMB.getCidade());
            entidade.setEndereco(endereco);
            entidadedao.iniciarTransacao();
            entidadedao.atualizar(entidade);
            entidadedao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.entidades.clear();
        } catch (Exception ex) {
            entidadedao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Entidade entidade) {
        try {
            entidadedao.iniciarTransacao();
            entidadedao.remover(entidade.getCodigo());
            entidadedao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.entidades.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Entidade> getEntidades() {
        this.consultar();
        
        if (this.entidades == null) {
            this.entidades = new ArrayList<Entidade>();
        }

        return entidades;
    }

    public void setRegiaoMB(RegiaoManagedBean regiaoMB) {
        this.regiaoMB = regiaoMB;
    }
    
    public List<Entidade> consultar() {
        if (paramBusca == null) {
            paramBusca = "";
        }
        if (paramBusca.equals("")) {
            entidades = (List<Entidade>) entidadedao.buscarTodos();
        } else {
            entidades = (List<Entidade>) entidadedao.buscarPorNome(paramBusca);
        }

        if (entidades.isEmpty()) {
            entidades = entidadedao.buscarPorCaractere(paramBusca);
        }
        return entidades;
    }
}
