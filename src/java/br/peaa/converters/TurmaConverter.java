package br.peaa.converters;

import br.peaa.DAO.TurmaDAO;
import br.peaa.entidades.Turma;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(forClass = Turma.class, value = "converterTurma")
public class TurmaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic,
            String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                TurmaDAO turmadao = new TurmaDAO();
                return turmadao.buscar(cd);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof Turma)) {
            Turma g = (Turma) o;
            return "" + g.getCodigo();
        }
        return null;
    }
}