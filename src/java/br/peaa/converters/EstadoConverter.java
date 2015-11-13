package br.peaa.converters;

import br.peaa.DAO.EstadoDAO;
import br.peaa.entidades.Estado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Estado.class, value = "converterEstado")
public class EstadoConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                EstadoDAO estadodao = new EstadoDAO();
                return estadodao.buscarPeloId(cd);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        if (o != null && (o instanceof Estado)) {
            Estado g = (Estado) o;
            return "" + g.getCodigo();
        }
        return null;
    }
}
