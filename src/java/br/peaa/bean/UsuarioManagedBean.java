package br.peaa.bean;

import br.peaa.DAO.UsuarioDAO;
import br.peaa.entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UsuarioManagedBean.class);

    private Usuario usuario;
    private List<Usuario> usuarios;
    private boolean novo;
    private String senhaTmp;
    private String confSenha;
    private UsuarioDAO usuariodao;

    public UsuarioManagedBean() {
        this.novo = true;
        usuario = new Usuario();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isNovo() {
        return novo;
    }

    public void criar() {
        try {
            if (confSenha != null && senhaTmp != null && senhaTmp.equals(confSenha)) {
                usuario.setSenha(senhaTmp);
                usuariodao.iniciarTransacao();
                usuariodao.salvar(usuario);
                usuariodao.confirmaTransacao();
            } else {
                usuariodao.iniciarTransacao();
                usuariodao.atualizar(usuario);
                usuariodao.confirmaTransacao();
            }
        } catch (Exception ex) {
            usuariodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public String editar(Usuario usuario) {
        novo = false;
        this.usuario = usuario;
        return "viewusuario.xhtml";
    }

    public void atualizar() {
        try {
            usuariodao.iniciarTransacao();
            usuariodao.atualizar(usuario);
            usuariodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
        } catch (Exception ex) {
            usuariodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Usuario usuario) {
        try {
            usuariodao.iniciarTransacao();
            usuariodao.remover(usuario.getCodigo());
            usuariodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.usuarios.clear();
        } catch (Exception ex) {
            usuariodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Usuario> getUsuarios() {
        this.usuarios = usuariodao.buscarTodos();
        if (this.usuarios == null) {
            this.usuarios = new ArrayList<Usuario>();
        }
        return usuarios;
    }
}
