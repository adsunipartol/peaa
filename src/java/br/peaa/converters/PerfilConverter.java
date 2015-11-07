package br.peaa.converters;

import br.peaa.DAO.PerfilDAO;
import br.peaa.entidades.Perfil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(forClass = Perfil.class, value = "converterPerfil")
public class PerfilConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic,
            String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                PerfilDAO perfildao = new PerfilDAO();
                return perfildao.buscar(cd);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof Perfil)) {
            Perfil g = (Perfil) o;
            return "" + g.getCodigo();
        }
        return null;
    }
}
