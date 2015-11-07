package br.peaa.bean;

import br.peaa.DAO.CidadeDAO;
import br.peaa.DAO.EstadoDAO;
import br.peaa.entidades.Cidade;
import br.peaa.entidades.Estado;
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

    private List<SelectItem> cidades = new ArrayList<SelectItem>();
    private Estado estado = new Estado();
    private Cidade cidade = new Cidade();
    private List<SelectItem> estadosSelecao;
    private List<SelectItem> cidadesSelecao;

    //Busca todos os estados
    public List<SelectItem> buscarEstados() {
        if (estadosSelecao == null) {
            estadosSelecao = new ArrayList<SelectItem>();
            EstadoDAO estadodao = new EstadoDAO();
            for (Estado est : estadodao.buscarTodos()) {
                estadosSelecao.add(new SelectItem(est.getCodigo(), est.getDescricao()));
            }
        }
        return estadosSelecao;
    }

    //Busca todas as cidades conforme o estado
    public void limparCidades() {
        cidades = null;
    }

    public List<SelectItem> getCidades() {
        if (cidades == null) {
            cidades = new ArrayList<SelectItem>();
            CidadeDAO cidadedao = new CidadeDAO();
            for (Cidade cid : cidadedao.buscarCidadePorEstado(this.estado.getCodigo())) {
                cidades.add(new SelectItem(cid.getCodigo(), cid.getDescricao()));
            }
        }
        return cidades;
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
