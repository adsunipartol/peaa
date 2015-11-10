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
    private List<Curso> cursoList;
    private List<SelectItem> turmas;
    private Curso curso = new Curso();
    private List<Curso> cursos;
    private List<SelectItem> cursosSelecao;
    private List<SelectItem> turnoSelecao;
    private List<SelectItem> entidadesSelecao;
    private String paramBusca;
    private Integer AnoTurma;
    private boolean editando;
    private CursoDAO cursodao;

    public CursoManagedBean() {
        editando = false;
        cursodao = new CursoDAO();
        cursoList = new ArrayList();
        turmas = new ArrayList<SelectItem>();
        paramBusca = "";
    }

    public void limparTurmas() {
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
            for (Curso c : cursodao.buscarTodos()) {
                cursosSelecao.add(new SelectItem(c.getCodigo(), c.getNome()));
            }
        }
        return cursosSelecao;
    }

    public List<SelectItem> getTurmas() {
        if (turmas == null && this.curso.getCodigo() != null) {
            turmas = new ArrayList<SelectItem>();
            TurmaDAO turmadao = new TurmaDAO();
            for (Turma tur : turmadao.buscarTurmaPorCurso(this.curso.getCodigo())) {
                turmas.add(new SelectItem(tur, tur.getSerie().toString() + "º - Ano " + tur.getAno()));
            }
        }
        return turmas;
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
        if (curso.getCodigo() == null) {
            try {
                cursodao.iniciarTransacao();
                cursodao.salvar(curso);
                cursodao.confirmaTransacao();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravado com sucesso", null));
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

    public void gerarTurmas() {
        int y = Calendar.getInstance().get(Calendar.YEAR);
        if (AnoTurma != null && AnoTurma >= y) {
            for (int i = 1; i <= curso.getNumSeries(); i++) {
                Turma t = new Turma();
                t.setAno(AnoTurma);
                t.setCurso(curso);
                t.setSerie(i);
                t.setStatus(Boolean.TRUE);
                TurmaDAO turmadao = new TurmaDAO();
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
    }

    public List<SelectItem> getEntidadesSelecao() {
        EntidadeDAO entidadedao = new EntidadeDAO();
        if (entidadesSelecao == null) {
            entidadesSelecao = new ArrayList<SelectItem>();
            for (Entidade c : entidadedao.buscarTodos()) {
                entidadesSelecao.add(new SelectItem(c, c.getNome()));
            }
        } else {
            entidadedao.buscarTodos();
        }
        return entidadesSelecao;
    }

    public void setEntidadesSelecao(List<SelectItem> entidadesSelecao) {
        this.entidadesSelecao = entidadesSelecao;
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
