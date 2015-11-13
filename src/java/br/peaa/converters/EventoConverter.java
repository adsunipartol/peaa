package br.peaa.converters;

import br.peaa.DAO.EventoDAO;
import br.peaa.entidades.Evento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Evento.class, value = "converterEvento")
public class EventoConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                EventoDAO eventodao = new EventoDAO();
                return eventodao.buscarPeloId(cd);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        if (o != null && (o instanceof Evento)) {
            Evento g = (Evento) o;
            return "" + g.getCodigo();
        }
        return null;
    }
    
}
