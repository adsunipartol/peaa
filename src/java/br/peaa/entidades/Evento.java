package br.peaa.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "EVENTO")
public class Evento implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_EVENTO", sequenceName = "SEQ_EVENTO")
    @GeneratedValue(generator = "SEQ_EVENTO")
    private Long codigo;

    @Column(length = 30, nullable = false)
    private String nome;
    
    @Column(length = 20, nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEvento;
    
    @Column (length = 20, nullable = false)
    private String status;

    @Column(length = 30, nullable = false)
    private String localEvento;

    @Column(length = 30, nullable = false)
    private String descricao;

    @Column (length = 30, nullable = false)
    private String ministrante;
    
    //Cardinalidade muitos para muitos entre EVENTO E CURSO
    @ManyToMany
    @JoinTable(name = "eventos_curso",
            joinColumns
            = @JoinColumn(name = "cod_evento",
                    referencedColumnName = "codigo",
                    unique = true),
            inverseJoinColumns
            = @JoinColumn(name = "cod_curso",
                    referencedColumnName = "codigo",
                    unique = true))
    private List<Curso> cursos;

    @OneToMany(mappedBy = "evento")
    private List<SubEvento> subeventos;
    

    @Column(length = 30, nullable = false)
    private Float custo;

//    Cardinalidade muitos para muitos entre EVENTO E PESSOA
    @ManyToMany
    @JoinTable(name = "eventos_pessoa",
            joinColumns
            = @JoinColumn(name = "cod_evento",
                    referencedColumnName = "codigo",
                    unique = true),
            inverseJoinColumns
            = @JoinColumn(name = "cod_pessoa",
                    referencedColumnName = "codigo",
                    unique = true))
    private List<Pessoa> pessoas;
    
    public Evento(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubEvento> getSubeventos() {
        return subeventos;
    }

    public void setSubeventos(List<SubEvento> subeventos) {
        this.subeventos = subeventos;
    }
    
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    
    
    public String getMinistrante() {
        return ministrante;
    }

    public void setMinistrante(String ministrante) {
        this.ministrante = ministrante;
    }
    
   
    public Date getData() {
        return dataEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }
    

    public void setData(Date data) {
        this.dataEvento = data;
    }

    public String getLocal() {
        return localEvento;
    }

    public void setLocal(String local) {
        this.localEvento = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }



    public Float getCusto() {
        return custo;
    }

    public void setCusto(Float custo) {
        this.custo = custo;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

}
