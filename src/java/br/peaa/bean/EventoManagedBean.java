package br.peaa.bean;

import br.peaa.DAO.EventoDAO;
import br.peaa.DAO.PessoaDAO;
import br.peaa.entidades.Curso;
import br.peaa.entidades.Evento;
import br.peaa.entidades.Pessoa;
import br.peaa.exceptions.ServicoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "eventoMB")
@SessionScoped
public class EventoManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(EventoManagedBean.class);

    private Evento evento;
    private List<Evento> eventos;
    private List<Evento> eventosSelecao;
    private boolean editando;
    private String raPessoa;
    private EventoDAO eventodao;
    private PessoaDAO pessoadao;
    private List<Pessoa> participantesEVT;
    private List<Curso> listCursos;
    private String paramBusca;

    public EventoManagedBean() {
        evento = new Evento();
        eventodao = new EventoDAO();
        pessoadao = new PessoaDAO();
        listCursos = new ArrayList<Curso>();
        paramBusca = "";
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

    public List<Evento> getEventosSelecao() {
        return eventosSelecao = (List<Evento>) eventodao.buscarTodos();
    }

    public void setEventosSelecao(List<Evento> eventosSelecao) {
        this.eventosSelecao = eventosSelecao;
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
    
    public String getParamBusca() {
        return paramBusca;
    }

    public void setParamBusca(String paramBusca) {
        this.paramBusca = paramBusca;
    }

    public String novo() {
        paramBusca = "";
        evento = new Evento();
        listCursos = new ArrayList<Curso>();
        return "cadastroevento.xhtml";
    }

    public String editar(Evento evento) {
        paramBusca = "";
        editando = true;
        this.evento = evento;
        if (this.evento.getCursos() == null) {
            this.evento.setCursos(new ArrayList<Curso>());
        }
        listCursos = evento.getCursos();
        return "cadastroevento.xhtml";
    }

    public String novaPresenca(Evento evt) {
        this.evento = evt;
        if (evento.getPessoas() == null) {
            this.evento.setPessoas(new ArrayList<Pessoa>());
        }
        return "regpresenca.xhtml";
    }

    public String participantes(Evento evt) {
        this.evento = evt;
        return "participaevt.xhtml";
    }

    public void regpresenca() {
        try {
            if (raPessoa != null) {
                Pessoa p = pessoadao.buscarPorRa(raPessoa);
                if (p != null) {
                    Integer p2 = evento.getPessoas().indexOf(p);
                    if (p2 == -1) {
                        evento.getPessoas().add(p);
                        eventodao.iniciarTransacao();
                        eventodao.atualizar(evento);
                        eventodao.confirmaTransacao();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Presença registrada para:\n " + p.getNome(), null));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Presença já registrada para:\n " + p.getNome() + ".", null));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi encontrada pessoa com a matricula " + raPessoa + ".", null));
                }
            }
        } catch (Exception ex) {
            eventodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void criar() {
        if (evento.getCodigo() == null) {
            try {
                evento.setCursos(listCursos);
                eventodao.iniciarTransacao();
                eventodao.salvar(evento);
                eventodao.confirmaTransacao();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            } catch (Exception ex) {
                eventodao.desfazTransacao();
                evento.setCodigo(null);
                logger.error(ex.getMessage(), ex);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            }
        } else {
            atualizar();
        }
    }

    public void atualizar() {
        try {
            evento.setCursos(listCursos);
            eventodao.iniciarTransacao();
            eventodao.atualizar(evento);
            eventodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.eventos.clear();
        } catch (Exception ex) {
            eventodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Evento evento) {
        try {
            eventodao.iniciarTransacao();
            eventodao.remover(evento.getCodigo());
            eventodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.eventos.clear();
        } catch (Exception ex) {
            eventodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Evento> getEventos() {
        this.consultar();
        
        if (this.eventos == null) {
            this.eventos = new ArrayList<Evento>();
        }

        return eventos;
    }

    public List<Pessoa> getParticipantesEVT() throws ServicoException {
        participantesEVT = pessoadao.buscarPessoasPorEvento(evento.getCodigo());
        return participantesEVT;
    }

    public void setParticipantesEVT(List<Pessoa> participantesEVT) {
        this.participantesEVT = participantesEVT;
    }
    
    public List<Evento> consultar() {
        if (paramBusca == null) {
            paramBusca = "";
        }
        if (paramBusca.equals("")) {
            eventos = (List<Evento>) eventodao.buscarTodos();
        } else {
            eventos = (List<Evento>) eventodao.buscarPorNome(paramBusca);
        }

        if (eventos.isEmpty()) {
            eventos = eventodao.buscarPorCaractere(paramBusca);
        }
        return eventos;
    }
}
