package br.peaa.bean;

import br.peaa.DAO.PessoaDAO;
import br.peaa.entidades.Curso;
import br.peaa.entidades.Endereco;
import br.peaa.entidades.Estado;
import br.peaa.entidades.Perfil;
import br.peaa.entidades.Pessoa;
import br.peaa.entidades.Turma;
import br.peaa.entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "pessoaMB")
@SessionScoped
public class PessoaManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PessoaManagedBean.class);

    private Pessoa pessoa;
    private List<Pessoa> pessoas;
    private Curso curso;
    private boolean novo;
    private Endereco endereco;
    private Usuario user;
    private String confSenha;
    private Turma turma;
    private Estado estado;
    private Perfil perfil;
    private String cpfTMP;
    private String cepTMP;
    private String telTMP;
    private String senhaTmp;
    private PessoaDAO pessoadao;

    public PessoaManagedBean() {
        this.novo = true;
        pessoa = new Pessoa();
        endereco = new Endereco();
        user = new Usuario();
        curso = new Curso();
        perfil = new Perfil();
        //Fredman:30/08/2015
        pessoadao = new PessoaDAO();
    }

    public String getCpfTMP() {
        return cpfTMP;
    }

    public void setCpfTMP(String cpfTMP) {
        this.cpfTMP = cpfTMP;
    }

    public String getCepTMP() {
        return cepTMP;
    }

    public String getSenhaTmp() {
        return senhaTmp;
    }

    public void setSenhaTmp(String senhaTmp) {
        this.senhaTmp = senhaTmp;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public void setCepTMP(String cepTMP) {
        this.cepTMP = cepTMP;
    }

    public String getTelTMP() {
        return telTMP;
    }

    public void setTelTMP(String telTMP) {
        this.telTMP = telTMP;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public boolean isNovo() {
        return novo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void criar() {
        try {
            if (pessoa.getCodigo() == null) {
                user.setStatus("Inativo");
                user.setLogin(pessoa.getRa());

                pessoa.setCpf(cpfTMP.replaceAll("[.-]", ""));
                pessoa.setTelefone(telTMP.replaceAll("[()]", ""));
                endereco.setCep(cepTMP.replaceAll("[-]", ""));

                pessoa.setTurma(new ArrayList<Turma>());
                pessoa.getTurma().add(turma);
                pessoa.setEndereco(endereco);
                pessoa.setUsuario(user);

                pessoadao.iniciarTransacao();
                pessoadao.salvar(pessoa);
                pessoadao.confirmaTransacao();
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect("sucesso.xhtml");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            } else {
                if (pessoa.getUsuario().getPerfis() == null) {
                    pessoa.getUsuario().setPerfis(new ArrayList<Perfil>());
                }
                pessoa.getUsuario().getPerfis().clear();
                pessoa.getUsuario().getPerfis().add(this.perfil);
                atualizar();
            }
//            if (confSenha != null && senhaTmp != null && senhaTmp.equals(confSenha)) {
//                pessoa.getUsuario().setSenha(senhaTmp);
//                atualizar();
//            } else {
//                atualizar();
//            }
        } catch (Exception ex) {
            pessoadao.desfazTransacao();
            pessoa.setCodigo(null);
            turma.setCodigo(null);
            user.setCodigo(null);
            endereco.setCodigo(null);
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }
    
    public String novo(){
        this.novo = true;
        return "cadastropessoa.xhtml";
    }

    public String editar(Pessoa pessoa) {
        novo = false;
        this.endereco = pessoa.getEndereco();
        this.pessoa = pessoa;
        return "lstpessoa.xhtml";
    }

    public String editarUsuario(Usuario usuario) {
        novo = false;
        this.user = usuario;
        return "viewusuario.xhtml";
    }

    public void atualizar() {
        try {
            pessoadao.iniciarTransacao();
            pessoadao.atualizar(pessoa);
            pessoadao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.pessoas.clear();
        } catch (Exception ex) {
            pessoadao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Pessoa pessoa) {
        try {
            pessoadao.iniciarTransacao();
            pessoadao.remover(pessoa);
            pessoadao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.pessoas.clear();
        } catch (Exception ex) {
            pessoadao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Pessoa> getPessoas() {
        this.pessoas = pessoadao.buscarTodos();
        
        if (this.pessoas == null) {
            this.pessoas = new ArrayList<Pessoa>();
        }

        return pessoas;
    }

}
