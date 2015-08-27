package br.peaa.bean;

import br.peaa.entidades.Perfil;
import br.peaa.entidades.Usuario;
import br.peaa.exceptions.ServicoException;
import br.peaa.servico.UsuarioServico;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "LoginMB")
@RequestScoped
public class AutenticacaoLogin {

    private String usuario;
    private String senha;
    //private Usuario usuarioLogado;

    private final String PRINCIPAL = "index.xhtml";
    private final String LOGIN = "login.xhtml";

    @ManagedProperty(value = "#{usuarioServico}")
    private UsuarioServico servUsuario;

    public AutenticacaoLogin() {

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return (Usuario) sessao.getAttribute("user");
    }

    public UsuarioServico getServUsuario() {
        return servUsuario;
    }

    public void setServUsuario(UsuarioServico servUsuario) {
        this.servUsuario = servUsuario;
    }

    public String login() throws ServicoException {
        try {
            Usuario usuarioLogado = servUsuario.validarUsuario(usuario, senha);
            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sessao.setAttribute("user", usuarioLogado);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), null));
            return LOGIN;
        }
        return PRINCIPAL;
    }

    public String logout() throws ServicoException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.invalidate();
        return LOGIN;
    }

    public boolean isAdmin() {
        if (getUsuarioLogado() != null) {
            for (Perfil p : getUsuarioLogado().getPerfis()) {
                if (p.getNome().equals("Administrador")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAcademico() {
        if (getUsuarioLogado() != null) {
            for (Perfil p : getUsuarioLogado().getPerfis()) {
                if (p.getNome().equals("Academico")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCoordenador() {
        if (getUsuarioLogado() != null) {
            for (Perfil p : getUsuarioLogado().getPerfis()) {
                if (p.getNome().equals("Coordenador")) {
                    return true;
                }
            }
        }
        return false;
    }
}
