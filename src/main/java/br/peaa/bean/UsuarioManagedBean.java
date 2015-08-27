package br.peaa.bean;

import br.peaa.entidades.Usuario;
import br.peaa.repositorio.UsuarioRepository;
import br.peaa.servico.UsuarioServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UsuarioManagedBean.class);

    @ManagedProperty(value = "#{usuarioServico}")
    private UsuarioServico srvUsuarios;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private UsuarioRepository repUsuario;
    private boolean novo;
    private String senhaTmp;
    private String confSenha;

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

    public void setSrvUsuarios(UsuarioServico srvUsuarios) {
        this.srvUsuarios = srvUsuarios;
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
                srvUsuarios.atualizar(usuario);
            }else {
                srvUsuarios.atualizar(usuario);
            }
        } catch (Exception ex) {
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
            srvUsuarios.atualizar(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Usuario usuario) {
        try {
            srvUsuarios.remover(usuario.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.usuarios.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Usuario> getUsuarios() {
        if ((this.usuarios == null || this.usuarios.isEmpty()) && srvUsuarios != null) {
            this.usuarios = srvUsuarios.buscarTodos();
        }
        if (this.usuarios == null) {
            this.usuarios = new ArrayList<Usuario>();
        }
        return usuarios;
    }

    public List<Usuario> listUsuariosByLogin(String login) {
        List<Usuario> user = new ArrayList<Usuario>();
        Usuario userByLogin = repUsuario.findByLogin(login);
        user.add(userByLogin);
        return user;
    }

    public void buscar() {

    }

}
