package br.peaa.converters;

import br.peaa.entidades.Turma;
import br.peaa.repositorio.TurmaRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(forClass = Turma.class, value = "converterTurma")
public class TurmaConverter implements Converter {
    
    TurmaRepository repositorioTurma;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic,
            String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                return repositorioTurma.findOne(cd);
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
