package br.peaa.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TURMA", uniqueConstraints = @UniqueConstraint(columnNames = {"serie", "curso_codigo", "ano"}))
public class Turma implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_TURMA", sequenceName = "SEQ_TURMA")
    @GeneratedValue(generator = "SEQ_TURMA")
    private Long codigo;

    @Column(length = 1, nullable = false)
    private Integer serie;

    @Column
    private Boolean status;

    @ManyToOne
    private Curso curso;

    @Column(length = 4, nullable = false)
    private Integer ano;

    @OneToMany(mappedBy = "turma")
    private List<Pessoa> pessoas;

    public Turma() {
        this.status = Boolean.FALSE;
    }

    public Turma(Long codigo, Integer serie, Boolean status, Curso curso, Integer ano) {
        this.codigo = codigo;
        this.serie = serie;
        this.status = status;
        this.curso = curso;
        this.ano = ano;
    }

    public Long getCodigo() {
        return codigo;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final Turma other = (Turma) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return serie + "º Ano " + ano;
    }
}
