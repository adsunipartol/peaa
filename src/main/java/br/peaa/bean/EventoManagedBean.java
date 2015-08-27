package br.peaa.bean;

import br.peaa.entidades.Curso;
import br.peaa.entidades.Evento;
import br.peaa.entidades.Pessoa;
import br.peaa.servico.EventoServico;
import br.peaa.servico.PessoaServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "eventoMB")
@SessionScoped
public class EventoManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoManagedBean.class);

    @ManagedProperty(value = "#{pessoaServico}")
    private PessoaServico srvPessoas;

    @ManagedProperty(value = "#{eventoServico}")
    private EventoServico srvEventos;

    private Evento evento;
    private List<Evento> eventos;
    private boolean editando;
    private List<SelectItem> eventosSelecao;
    private String raPessoa;

    private List<Pessoa> participantesEVT = new ArrayList<Pessoa>();

    private List<Curso> listCursos;

    public EventoManagedBean() {

    }

    public void setSrvEventos(EventoServico srvEventos) {
        this.srvEventos = srvEventos;
    }

    public String getRaPessoa() {
        return raPessoa;
    }

    public void setRaPessoa(String raPessoa) {
        this.raPessoa = raPessoa;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public PessoaServico getSrvPessoas() {
        return srvPessoas;
    }

    public void setSrvPessoas(PessoaServico srvPessoas) {
        this.srvPessoas = srvPessoas;
    }

    public List<Curso> getListCursos() {
        return listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public String novo() {
        evento = new Evento();
        //listCursos = new ArrayList<Curso>();
        return "cadastroevento.xhtml";
    }

    public String editar(Evento evento) {
        editando = true;
        this.evento = evento;
        if (this.evento.getCursos() == null) {
            this.evento.setCursos(new ArrayList<Curso>());
        }

        return "cadastroevento.xhtml";
    }

    public String novaPresenca(Evento evt) {
        this.evento = evt;
        this.evento.setPessoas(new ArrayList<Pessoa>());
        return "regpresenca.xhtml";
    }

    public String participantes(Evento evt) {
        this.evento = evt;
        return "participaevt.xhtml";
    }

    public void regpresenca() {
        try {
            if (raPessoa != null) {
                Pessoa p = srvPessoas.buscarPorRa(raPessoa);
                if (p != null) {
                    evento.getPessoas().add(p);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " " + p.getNome(), null));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Matricula nÃ£o encontrada", null));
                }
                srvEventos.atualizar(evento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void criar() {
        try {
            if (evento.getCodigo() == null) {
                evento.setCursos(listCursos);
                srvEventos.criar(evento);
            } else {
                atualizar();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void atualizar() {
        try {
            srvEventos.atualizar(evento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.eventos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Evento evento) {
        try {
            srvEventos.remover(evento.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.eventos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Evento> getEventos() {
        if ((this.eventos == null || this.eventos.isEmpty()) && srvEventos != null) {
            this.eventos = srvEventos.buscarTodos();
        }
        if (this.eventos == null) {
            this.eventos = new ArrayList<Evento>();
        }

        return eventos;
    }

    public List<SelectItem> getEventosSelecao() {
        if (eventosSelecao == null) {
            eventosSelecao = new ArrayList<SelectItem>();
            for (Evento e : srvEventos.buscarTodos()) {
                eventosSelecao.add(new SelectItem(e.getCodigo(), e.getNome()));
            }
        }
        return eventosSelecao;
    }

    public List<Pessoa> getParticipantesEVT() {
        participantesEVT = srvPessoas.buscarPessoaPorEvento(evento.getCodigo());
        return participantesEVT;
    }

    public void setParticipantesEVT(List<Pessoa> participantesEVT) {
        this.participantesEVT = participantesEVT;
    }

}
