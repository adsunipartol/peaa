package br.peaa.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CURSO")
public class Curso implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_CURSO", sequenceName = "SEQ_CURSO")
    @GeneratedValue(generator = "SEQ_CURSO")
    private Long codigo;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 30, nullable = false)
    private String turno;

    @Column(length = 30, nullable = false)
    private Integer numSeries;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_entidade", nullable = false)
    private Entidade entidade;

    @ManyToMany(mappedBy = "cursos")
    private List<Evento> eventos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private List<Turma> turmas;

    public Curso() {
    }

    public Curso(Long codigo) {
        this.codigo = codigo;
    }

    public Curso(Long codigo, String nome, Integer numSeries) {
        this.codigo = codigo;
        this.nome = nome;
        this.numSeries = numSeries;
    }

    public Long getCodigo() {
        return codigo;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(Integer numSeries) {
        this.numSeries = numSeries;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.codigo);
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
        final Curso other = (Curso) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public String toString() {
        return nome;
    }
}
