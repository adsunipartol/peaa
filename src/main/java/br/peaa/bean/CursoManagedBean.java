package br.peaa.bean;

import br.peaa.entidades.Curso;
import br.peaa.entidades.Entidade;
import br.peaa.entidades.Turma;
import br.peaa.servico.CursoServico;
import br.peaa.servico.EntidadeServico;
import br.peaa.servico.TurmaServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "CursoMB")
@SessionScoped
public class CursoManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(CursoManagedBean.class);

    @ManagedProperty(value = "#{cursoServico}")
    private CursoServico srvCursos;

    @ManagedProperty(value = "#{entidadeServico}")
    private EntidadeServico srvEntidades;

    private Calendar ano;
    
    private List<Curso> cursoList = new ArrayList();

    @ManagedProperty(value = "#{turmaServico}")
    private TurmaServico srvTurma;

    private List<SelectItem> turmas = new ArrayList<SelectItem>();

    private Curso curso = new Curso();
    private List<Curso> cursos;

    private List<SelectItem> cursosSelecao;
    private List<SelectItem> turnoSelecao;
    private List<SelectItem> entidadesSelecao;

    private String paramBusca;

    private Integer AnoTurma;

    private boolean editando;

    public CursoManagedBean() {
        editando = false;
    }

    public CursoServico getSrvCursos() {
        return srvCursos;
    }

    public void buscarTurmasPorCurso() {
        turmas = null;
    }

    public List<SelectItem> getCursosSelecao() {
        return cursosSelecao;
    }

    public void setCursosSelecao(List<SelectItem> cursosSelecao) {
        this.cursosSelecao = cursosSelecao;
    }

    public List<SelectItem> buscarCursos() {
        if (cursosSelecao == null) {
            cursosSelecao = new ArrayList<SelectItem>();
            for (Curso c : srvCursos.buscarTodos()) {
                cursosSelecao.add(new SelectItem(c.getCodigo(), c.getNome()));
            }
        }
        return cursosSelecao;
    }

    public List<SelectItem> getTurmas() {
        if (turmas == null && this.curso.getCodigo() != null) {
            turmas = new ArrayList<SelectItem>();

            for (Turma tur : srvTurma.buscarTurmaPorCurso(this.curso.getCodigo())) {
                turmas.add(new SelectItem(tur, tur.getSerie().toString() + "º - Ano " + tur.getAno()));
            }
        }
        return turmas;
    }

    public void setSrvCursos(CursoServico srvCursos) {
        this.srvCursos = srvCursos;
    }

    public List<Curso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<Curso> cursoList) {
        this.cursoList = cursoList;
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
        try {
            if (curso.getCodigo() == null) {
                srvCursos.criar(curso);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            } else {
                atualizar();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void atualizar() {
        try {
            srvCursos.atualizar(curso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
            this.cursos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void remover(Curso curso) {
        try {
            srvCursos.remover(curso.getCodigo());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido com sucesso", null));
            this.cursos.clear();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public List<Curso> getCursos() {
        if ((this.cursos == null || this.cursos.isEmpty()) && srvCursos != null) {
            this.cursos = srvCursos.buscarTodos();
        }
        if (this.cursos == null) {
            this.cursos = new ArrayList<Curso>();
        } else {
            this.cursos = srvCursos.buscarTodos();
        }
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public String editar(Curso c) {
        editando = true;
        this.curso = c;
        return "cadastrocurso.xhtml";
    }

    public String novo() {
        curso = new Curso();
        return "cadastrocurso.xhtml";
    }

    public Integer getAnoTurma() {
        return AnoTurma;
    }

    public void setAnoTurma(Integer AnoTurma) {
        this.AnoTurma = AnoTurma;
    }

    public List<SelectItem> getTurnoSelecao() {
        if (turnoSelecao == null) {
            turnoSelecao = new LinkedList<SelectItem>();
            turnoSelecao.add(new SelectItem("Noturno", "Noturno"));
            turnoSelecao.add(new SelectItem("Matutino", "Matutino"));
            turnoSelecao.add(new SelectItem("Vespertino", "Vespertino"));
        }
        return turnoSelecao;
    }

    public TurmaServico getSrvTurma() {
        return srvTurma;
    }

    public void setSrvTurma(TurmaServico srvTurma) {
        this.srvTurma = srvTurma;
    }

    public void gerarTurmas() {
        int y = Calendar.getInstance().get(Calendar.YEAR);
        if (AnoTurma != null && AnoTurma >= y) {
            for (int i = 1; i <= curso.getNumSeries(); i++) {
                Turma t = new Turma();
                t.setAno(AnoTurma);
                t.setCurso(curso);
                t.setSerie(i);
                t.setStatus(Boolean.TRUE);
                try {
                    srvTurma.criar(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Turmas geradas com sucesso", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ano invalido", null));
        }
    }

    public EntidadeServico getSrvEntidades() {
        return srvEntidades;
    }

    public void setSrvEntidades(EntidadeServico srvEntidades) {
        this.srvEntidades = srvEntidades;
    }

    public List<SelectItem> getEntidadesSelecao() {
        if (entidadesSelecao == null) {
            entidadesSelecao = new ArrayList<SelectItem>();
            for (Entidade c : srvEntidades.buscarTodos()) {
                entidadesSelecao.add(new SelectItem(c, c.getNome()));
            }
        } else {
            srvEntidades.buscarTodos();
        }
        return entidadesSelecao;
    }

    public void setEntidadesSelecao(List<SelectItem> entidadesSelecao) {
        this.entidadesSelecao = entidadesSelecao;
    }

    public List<Curso> search() {
        if (paramBusca.equals("")) {
            cursos = srvCursos.buscarTodos();
        } else if (paramBusca.equals(curso.getNome())) {
            cursos = (List<Curso>) srvCursos.buscarPorNome(paramBusca);
        } else {
            cursos = srvCursos.buscarPorCaractere(paramBusca);
        }
        return cursos;
    }

}
