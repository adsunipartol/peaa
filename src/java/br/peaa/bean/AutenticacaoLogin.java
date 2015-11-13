package br.peaa.bean;

import br.peaa.DAO.UsuarioDAO;
import br.peaa.entidades.Perfil;
import br.peaa.entidades.Usuario;
import br.peaa.exceptions.ServicoException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "LoginMB")
@SessionScoped
public class AutenticacaoLogin {

    private String usuario;
    private String senha;
    private final String PRINCIPAL = "index.xhtml";
    private final String LOGIN = "login.xhtml";
    private UsuarioDAO usuariodao;
    private String pagina;

    public AutenticacaoLogin() {
        usuariodao = new UsuarioDAO();
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

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }
    
    public Usuario getUsuarioLogado() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Usuario u;
        u = (Usuario) sessao.getAttribute("user");
        return u;
    }

    public String login() throws ServicoException {
        try {
            Usuario usuarioLogado = usuariodao.validarUsuario(usuario, senha);
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
            Perfil p = getUsuarioLogado().getPerfil();
            if (p.getNome().equals("Administrador")) {
                return true;
            }
        }
        return false;
    }

    public boolean isAcademico() {
        if (getUsuarioLogado() != null) {
            Perfil p = getUsuarioLogado().getPerfil();
            if (p.getNome().equals("Academico")) {
                return true;
            }
        }
        return false;
    }

    public boolean isCoordenador() {
        if (getUsuarioLogado() != null) {
            Perfil p = getUsuarioLogado().getPerfil();
            if (p.getNome().equals("Coordenador")) {
                return true;
            }
        }
        return false;
    }

    public String controlaAcessoPagina() {
        pagina = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        pagina = pagina.replace("/", "");
        pagina = pagina.replace(".xhtml", "");
        if (isAcademico()) {
            if (!pagina.equals("index") && !pagina.equals("mypresencas")) {
                return "index.xhtml";
            }
        } else if (isCoordenador()) {
            if(pagina.equals("mypresencas")){
                return "index.xhtml";
            }
        } else if (isAdmin()) {
            if(pagina.equals("mypresencas")){
                return "index.xhtml";
            }
        }
        return null;
    }
}
