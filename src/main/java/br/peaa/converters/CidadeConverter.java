package br.peaa.converters;

import br.peaa.entidades.Cidade;
import br.peaa.repositorio.CidadeRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(forClass = Cidade.class, value = "converterCidade")
public class CidadeConverter implements Converter {
    
    CidadeRepository repositorioCidade;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic,
            String value) {
        if (value != null) {
            try {
                Long cd = Long.parseLong(value);
                return repositorioCidade.findOne(cd);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof Cidade)) {
            Cidade g = (Cidade) o;
            return "" + g.getCodigo();
        }
        return null;
    }
}
