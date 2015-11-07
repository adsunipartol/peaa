package br.peaa.converters;

import br.peaa.DAO.EntidadeDAO;
import br.peaa.entidades.Entidade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(forClass = Entidade.class, value = "converterEntidade")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic,
            String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                EntidadeDAO entidadedao = new EntidadeDAO();
                return entidadedao.buscarPeloId(cd);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            Entidade g = (Entidade) o;
            return "" + g.getCodigo();
        }
        return null;
    }
}
