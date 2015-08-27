package br.peaa.bean;

import br.peaa.entidades.Cidade;
import br.peaa.entidades.Estado;
import br.peaa.servico.CidadeServico;
import br.peaa.servico.EstadoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import javax.faces.model.SelectItem;

@ManagedBean(name = "RegiaoMB")
@SessionScoped
public class RegiaoManagedBean implements Serializable {

    private static final long serialVersionUID = -4281390476508498320L;

    @ManagedProperty(value = "#{estadoServico}")
    private EstadoServico srvEstados;

    @ManagedProperty(value = "#{cidadeServico}")
    private CidadeServico srvCidades;

    private List<SelectItem> cidades = new ArrayList<SelectItem>();
    private Estado estado = new Estado();
    private Cidade cidade = new Cidade();
    private List<SelectItem> estadosSelecao;
    private List<SelectItem> cidadesSelecao;

    //Busca todos os estados
    public List<SelectItem> buscarEstados() {
        if (estadosSelecao == null) {
            estadosSelecao = new ArrayList<SelectItem>();
            for (Estado est : srvEstados.buscarTodos()) {
                estadosSelecao.add(new SelectItem(est.getCodigo(), est.getDescricao()));
            }
        }
        return estadosSelecao;
    }

    //Busca todas as cidades conforme o estado
    public void buscarCidadesPorEstado() {
        cidades = null;
    }

    public List<SelectItem> getCidades() {
        if (cidades == null) {
            cidades = new ArrayList<SelectItem>();
            for (Cidade cid : srvCidades.buscarCidadePorEstado(this.estado.getCodigo())) {
                cidades.add(new SelectItem(cid.getCodigo(), cid.getDescricao()));
            }
        }
        return cidades;
    }

    public EstadoServico getSrvEstados() {
        return srvEstados;
    }

    public void setSrvEstados(EstadoServico srvEstados) {
        this.srvEstados = srvEstados;
    }

    public CidadeServico getSrvCidades() {
        return srvCidades;
    }

    public void setSrvCidades(CidadeServico srvCidades) {
        this.srvCidades = srvCidades;
    }

    public List<SelectItem> getEstadosSelecao() {
        return estadosSelecao;
    }

    public void setEstadosSelecao(List<SelectItem> estadosSelecao) {
        this.estadosSelecao = estadosSelecao;
    }

    public List<SelectItem> getCidadesSelecao() {
        return cidadesSelecao;
    }

    public void setCidadesSelecao(List<SelectItem> cidadesSelecao) {
        this.cidadesSelecao = cidadesSelecao;
    }

    public void setCidades(List<SelectItem> cidades) {
        this.cidades = cidades;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
