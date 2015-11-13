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
    private String paramBusca;

    @ManagedProperty(value = "#{RegiaoMB}")
    private RegiaoManagedBean regiaoMB;

    @ManagedProperty(value = "#{CursoMB}")
    private CursoManagedBean cursoMB;

    public PessoaManagedBean() {
        pessoa = new Pessoa();
        pessoas = new ArrayList<Pessoa>();
        curso = new Curso();
        this.novo = true;
        endereco = new Endereco();
        user = new Usuario();
        turma = new Turma();
        estado = new Estado();
        perfil = new Perfil();
        pessoadao = new PessoaDAO();
        regiaoMB = new RegiaoManagedBean();
        cursoMB = new CursoManagedBean();
        paramBusca = "";
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

    public RegiaoManagedBean getRegiaoMB() {
        return regiaoMB;
    }

    public void setRegiaoMB(RegiaoManagedBean regiaoMB) {
        this.regiaoMB = regiaoMB;
    }

    public CursoManagedBean getCursoMB() {
        return cursoMB;
    }

    public void setCursoMB(CursoManagedBean cursoMB) {
        this.cursoMB = cursoMB;
    }

    public String getParamBusca() {
        return paramBusca;
    }

    public void setParamBusca(String paramBusca) {
        this.paramBusca = paramBusca;
    }

    public String novo() {
        paramBusca = "";
        novo = true;
        pessoa = new Pessoa();
        curso = new Curso();
        endereco = new Endereco();
        user = new Usuario();
        turma = new Turma();
        estado = new Estado();
        perfil = new Perfil();
        cpfTMP = "";
        cepTMP = "";
        telTMP = "";
        senhaTmp = "";
        regiaoMB.setEstado(null);
        regiaoMB.setCidade(null);
        regiaoMB.setCidades(null);
        regiaoMB.setEstados(null);
        cursoMB.setCurso(curso);
        cursoMB.setTurmas(null);
        cursoMB.setParamBusca("");
        return "cadastropessoa.xhtml";
    }

    public String editar(Pessoa pessoa) {
        paramBusca = "";
        novo = false;
        this.pessoa = pessoa;
        user = pessoa.getUsuario();
        perfil = user.getPerfil();
        confSenha = user.getSenha();
        endereco = pessoa.getEndereco();
        turma = pessoa.getTurma();
        if (turma != null) {
            curso = pessoa.getTurma().getCurso();
        } else {
            curso = new Curso();
        }
        regiaoMB.setEstado(pessoa.getEndereco().getCidade().getEstado());
        regiaoMB.setCidades(null);
        regiaoMB.getCidades();
        regiaoMB.setCidade(pessoa.getEndereco().getCidade());
        cursoMB.setCurso(curso);
        cursoMB.setTurmas(null);
        cursoMB.getTurmas();
        cursoMB.setParamBusca("");
        return "lstpessoa.xhtml";
    }

    public String editarUsuario(Pessoa pessoa) {
        paramBusca = "";
        novo = false;
        this.pessoa = pessoa;
        user = pessoa.getUsuario();
        endereco = pessoa.getEndereco();
        senhaTmp = user.getSenha();
        confSenha = user.getSenha();
        cursoMB.setParamBusca("");
        return "viewusuario.xhtml";
    }

    public void criar() {
        if (confSenha != null && user.getSenha() != null && user.getSenha().equals(confSenha)) {
            if (pessoa.getCodigo() == null) {
                try {
                    user.setStatus("Inativo");
                    user.setLogin(pessoa.getRa());
                    //seta o perfil como acadêmico por padrão
                    perfil.setCodigo(2l);
                    user.setPerfil(perfil);
                    pessoa.setCpf(cpfTMP.replaceAll("[.-]", ""));
                    pessoa.setTelefone(telTMP.replaceAll("[()]", ""));
                    endereco.setCep(cepTMP.replaceAll("[-]", ""));
                    endereco.setCidade(regiaoMB.getCidade());

                    pessoa.setTurma(turma);
                    pessoa.setEndereco(endereco);
                    pessoa.setUsuario(user);
                    pessoa.getUsuario().setSenha(user.getSenha());

                    pessoadao.iniciarTransacao();
                    pessoadao.salvar(pessoa);
                    pessoadao.confirmaTransacao();
                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                    context.redirect("sucesso.xhtml");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
                } catch (Exception ex) {
                    pessoadao.desfazTransacao();
                    pessoa.setCodigo(null);
                    endereco.setCodigo(null);
                    user.setCodigo(null);
                    if (turma != null) {
                        turma.setCodigo(null);
                    }
                    logger.error(ex.getMessage(), ex);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
                }
            } else {
                atualizar();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "As senhas são diferentes entre si.", null));
        }
    }

    public void atualizar() {
        try {
            if (turma != null && turma.getCodigo() != null) {
                pessoa.setTurma(turma);
            }
            if (perfil != null && perfil.getCodigo() != null) {
                user.setPerfil(perfil);
                pessoa.setUsuario(user);
            }
            if (regiaoMB.getCidade() != null && regiaoMB.getCidade().getCodigo() != null) {
                endereco.setCidade(regiaoMB.getCidade());
                pessoa.setEndereco(endereco);
            }
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
        this.consultar();

        if (this.pessoas == null) {
            this.pessoas = new ArrayList<Pessoa>();
        }

        return pessoas;
    }

    public void salvarUsuario(Pessoa p) {
        if (confSenha.equals("") && user.getSenha().equals("")) {
            confSenha = senhaTmp;
            user.setSenha(confSenha);
        }
        criar();
    }

    public List<Pessoa> consultar() {
        if (paramBusca == null) {
            paramBusca = "";
        }
        if (paramBusca.equals("")) {
            pessoas = (List<Pessoa>) pessoadao.buscarTodos();
        } else {
            pessoas = (List<Pessoa>) pessoadao.buscarPorNome(paramBusca);
        }

        if (pessoas.isEmpty()) {
            pessoas = pessoadao.buscarPorCaractere(paramBusca);
        }
        return pessoas;
    }
    
    public void onLoad(){
        AutenticacaoLogin autLogin = new AutenticacaoLogin();
        Usuario u = autLogin.getUsuarioLogado();
        pessoa = pessoadao.buscarPorUsuario(u.getCodigo());
    }
}
