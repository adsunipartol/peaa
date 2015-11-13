package br.peaa.converters;

import br.peaa.DAO.CursoDAO;
import br.peaa.entidades.Curso;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Curso.class, value = "converterCurso")
public class CursoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                CursoDAO cursodao = new CursoDAO();
                Curso c = cursodao.buscarPeloId(cd);
                return c;
            } catch (Throwable t) {
                
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
