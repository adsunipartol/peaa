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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER")
    @GeneratedValue(generator = "SEQ_USER")
    private Long codigo;

    @Column(length = 15, nullable = false)
    private String login;
    
    @Column (length = 30, nullable = true)
    private String status;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "perfil_de_usuario",
            joinColumns
            = @JoinColumn(name = "cod_usuario",
                    referencedColumnName = "codigo",
                    unique = true),
            inverseJoinColumns
            = @JoinColumn(name = "cod_perfil",
                    referencedColumnName = "codigo",
                    unique = true))
    private List<Perfil> perfis;

    @Column(length = 10, nullable = false)
    private String senha;

   @OneToOne
   private Pessoa pessoa;
    
    public Usuario (){
        
    }
    
    public Usuario (String login){
        this.login = login;
    }
    
    public Usuario(Long codigo, String login, String senha) {
        this.codigo = codigo;
        this.login = login;
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    public Long getCodigo() {
        return codigo;
    }
    

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codigo);
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
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.codigo, other.codigo);
    }

}
