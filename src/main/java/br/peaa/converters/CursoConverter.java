package br.peaa.converters;

import br.peaa.entidades.Curso;
import br.peaa.repositorio.CursoRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;


@FacesConverter(forClass = Curso.class, value = "converterCurso")
public class CursoConverter implements Converter {
    
    @Autowired
    CursoRepository repositorioCurso;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic,
            String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                Curso c = repositorioCurso.findOne(cd);
                return c;
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof Curso)) {
            Curso g = (Curso) o;
            return "" + g.getCodigo();
        }
        return null;
    }
}
