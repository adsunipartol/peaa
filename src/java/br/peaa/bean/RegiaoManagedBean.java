package br.peaa.bean;

import br.peaa.DAO.CidadeDAO;
import br.peaa.DAO.EstadoDAO;
import br.peaa.entidades.Cidade;
import br.peaa.entidades.Estado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "RegiaoMB")
@SessionScoped
public class RegiaoManagedBean implements Serializable {

    private List<Cidade> cidades;
    private List<Estado> estados;
    private Estado estado;
    private Cidade cidade;

    public RegiaoManagedBean() {
        cidades = new ArrayList<Cidade>();
        estados = new ArrayList<Estado>();
        estado = new Estado();
        cidade = new Cidade();
    }

    //Busca todos os estados
    public List<Estado> getEstados() {
        if (estados == null || estados.isEmpty()) {
            estados = new ArrayList<Estado>();
            EstadoDAO estadodao = new EstadoDAO();
            estados = estadodao.buscarTodos();
        }
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        if (cidades == null && this.estado != null && this.estado.getCodigo() != null) {
            cidades = new ArrayList<Cidade>();
            CidadeDAO cidadedao = new CidadeDAO();
            cidades = cidadedao.buscarCidadePorEstado(this.estado.getCodigo());
        }
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
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
