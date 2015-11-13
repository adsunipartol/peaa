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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table (name="PESSOA")
public class Pessoa implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_PESSOA", sequenceName = "SEQ_PESSOA")
    @GeneratedValue(generator = "SEQ_PESSOA")
    @Column (length=10, nullable=false)
    private Long codigo;
    
    @Column (length=80, nullable=false)
    private String nome;
    
    @Column (length=20, nullable=false) 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtNascimento;
    
    @Column (length=30, nullable=false)
    private Long rg;
    
    @Column (length = 20, nullable = false, unique = true)
    private String ra;
    
    @Column(length= 11, nullable=false, unique = true)
    private String cpf;
   
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "pessoas")
    private List<Evento> eventos;
    
    @OneToOne (cascade = CascadeType.ALL)
    private Endereco endereco;
    
    @Column(length= 10, nullable=false)
    private String tipo;
    
    @Column(length= 30, nullable=false)
    private String email;
    
    @ManyToOne (cascade = CascadeType.PERSIST)
    private Turma turma;
    
    @Column(length= 15, nullable=false)
    private String telefone;
    
    @OneToOne (cascade = CascadeType.ALL)
    private Usuario usuario;

    public Long getCodigo() {
        return codigo;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Long getRg() {
        return rg;
    }

    public void setRg(Long rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuario.setLogin(ra);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.codigo);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "dtNascimento=" + dtNascimento + '}';
    }
}