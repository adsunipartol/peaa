package br.peaa.bean;

import br.peaa.DAO.CursoDAO;
import br.peaa.DAO.EntidadeDAO;
import br.peaa.DAO.TurmaDAO;
import br.peaa.entidades.Curso;
import br.peaa.entidades.Entidade;
import br.peaa.entidades.Turma;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "CursoMB")
@SessionScoped
public class CursoManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CursoManagedBean.class);

    private Calendar ano;
    private List<Turma> turmas;
    private Curso curso;
    private List<Curso> cursos;
    private List<Curso> cursosSelecao;
    private List<SelectItem> turno;
    private List<Entidade> entidades;
    private String paramBusca;
    private Integer AnoTurma;
    private boolean editando;
    private CursoDAO cursodao;

    public CursoManagedBean() {
        editando = false;
        curso = new Curso();
        cursodao = new CursoDAO();
        turmas = new ArrayList<Turma>();
        paramBusca = "";
    }

    public List<Turma> getTurmas() {
        if ((turmas == null || turmas.isEmpty()) && (this.curso != null && this.curso.getCodigo() != null)) {
            turmas = new ArrayList<Turma>();
            TurmaDAO turmadao = new TurmaDAO();
            turmas = turmadao.buscarTurmaPorCurso(this.curso.getCodigo());
        }
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public String getParamBusca() {
        return paramBusca;
    }

    public void setParamBusca(String paramBusca) {
        this.paramBusca = paramBusca;
    }

    public Curso getCurso() {
        return curso;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void criar() {
        if (curso.getCodigo() == null) {
            try {
                if (curso.getEntidade() == null || curso.getEntidade().getCodigo() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "É necessário informar a Entidade.", null));
                } else {
                    cursodao.iniciarTransacao();
                    cursodao.salvar(curso);
                    cursodao.confirmaTransacao();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
                }
            } catch (Exception ex) {
                //faz o rollback e fecha a sessão para que seja aberta uma tentativa posterior
                cursodao.desfazTransacao();
                //seta o código pra null para ao tentar salvar novamente não caia no atualizar()
                curso.setCodigo(null);
                logger.error(ex.getMessage(), ex);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            }
        } else {
            atualizar();
        }
    }

    public void atualizar() {
        try {
            cursodao.iniciarTransacao();
            cursodao.atualizar(curso);
            cursodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.cursos.clear();
        } catch (Exception ex) {
            cursodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Curso curso) {
        try {
            cursodao.iniciarTransacao();
            cursodao.remover(curso);
            cursodao.confirmaTransacao();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.cursos.clear();
        } catch (Exception ex) {
            cursodao.desfazTransacao();
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Curso> getCursos() {
        this.consultar();

        if (this.cursos == null) {
            this.cursos = new ArrayList<Curso>();
        }

        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Curso> getCursosSelecao() {
        return cursosSelecao = (List<Curso>) cursodao.buscarTodos();
    }

    public void setCursosSelecao(List<Curso> cursosSelecao) {
        this.cursosSelecao = cursosSelecao;
    }
    
    public String editar(Curso c) {
        paramBusca = "";
        editando = true;
        this.curso = c;
        AnoTurma = null;
        return "cadastrocurso.xhtml";
    }

    public String novo() {
        paramBusca = "";
        curso = new Curso();
        editando = false;
        AnoTurma = null;
        return "cadastrocurso.xhtml";
    }

    public Integer getAnoTurma() {
        return AnoTurma;
    }

    public void setAnoTurma(Integer AnoTurma) {
        this.AnoTurma = AnoTurma;
    }

    public List<SelectItem> getTurno() {
        if (turno == null) {
            turno = new LinkedList<SelectItem>();
            turno.add(new SelectItem("Noturno", "Noturno"));
            turno.add(new SelectItem("Matutino", "Matutino"));
            turno.add(new SelectItem("Vespertino", "Vespertino"));
        }
        return turno;
    }

    public void gerarTurmas() {
        int y = Calendar.getInstance().get(Calendar.YEAR);
        TurmaDAO turmadao = new TurmaDAO();
        //Verificar se já existem turmas geradas para o curso naquele ano
        List<Turma> listaTurma = turmadao.buscarTurmaPorCurso(curso.getCodigo());
        boolean achouTurmaCursoAno = false;
        for (Turma tmp : listaTurma) {
            if (tmp.getAno().equals(AnoTurma)) {
                achouTurmaCursoAno = true;
                break;
            }
        }
        if (!achouTurmaCursoAno) {
            if (AnoTurma != null && AnoTurma >= y) {
                for (int i = 1; i <= curso.getNumSeries(); i++) {
                    Turma t = new Turma();
                    t.setAno(AnoTurma);
                    t.setCurso(curso);
                    t.setSerie(i);
                    t.setStatus(Boolean.TRUE);
                    turmadao = new TurmaDAO();
                    try {
                        turmadao.salvar(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Turmas geradas com sucesso", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ano invalido", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Já existem turmas do ano de " + AnoTurma + " no curso " + curso.getNome() + " cadastradas.", null));
        }
    }

    public List<Entidade> getEntidades() {
        EntidadeDAO entidadedao = new EntidadeDAO();
        entidades = entidadedao.buscarTodos();

        return entidades;
    }

    public void setEntidades(List<Entidade> entidades) {
        this.entidades = entidades;
    }

    public List<Curso> consultar() {
        if (paramBusca == null) {
            paramBusca = "";
        }
        if (paramBusca.equals("")) {
            cursos = (List<Curso>) cursodao.buscarTodos();
        } else {
            cursos = (List<Curso>) cursodao.buscarPorNome(paramBusca);
        }

        if (cursos.isEmpty()) {
            cursos = cursodao.buscarPorCaractere(paramBusca);
        }
        return cursos;
    }
}
