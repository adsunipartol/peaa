
package br.peaa.converters;

import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean(name = "indexConverter")
@RequestScoped
//@Component(value = "indexConverter")
public class SelectedItemIndexConverter implements Converter {

    //private int index = -1;
    private Map<Object, Integer> indexes;

    public SelectedItemIndexConverter() {
        indexes = new HashMap<Object, Integer>();
    }

    /*
     * (non-Javadoc) @see
     * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     * javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        try {
            SelectItem selectedItem = this.getSelectedItemByIndex(component, Integer.parseInt(value));
            if (selectedItem != null) {
                Object itemValue = selectedItem.getValue();
                if (!(itemValue instanceof String)
                        || itemValue instanceof String && !((String) itemValue).isEmpty()) {
                    return selectedItem.getValue();
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /*
     * (non-Javadoc) @see
     * javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
     * javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {
        Integer index = indexes.get(component);
        if (index == null) {
            index = new Integer("0");
        }
        index++;
        indexes.put(component, index);
        return String.valueOf(index);
    }

    /**
     * Obtem o SelecItem de acordo com a opção selecionada pelo usuário
     */
    protected SelectItem getSelectedItemByIndex(UIComponent component, int index) {

        List<SelectItem> items = this.getSelectItems(component);
        int size = items.size();

        if (index > -1
                && size > index) {
            return items.get(index);
        }

        return null;
    }

    protected List<SelectItem> getSelectItems(UIComponent component) {

        List<SelectItem> items = new ArrayList<SelectItem>();

        int childCount = component.getChildCount();
        if (childCount == 0) {
            return items;
        }

        List<UIComponent> children = component.getChildren();
        for (UIComponent child : children) {
            if (child instanceof UISelectItem) {
                this.addSelectItem((UISelectItem) child, items);
            } else if (child instanceof UISelectItems) {
                this.addSelectItems((UISelectItems) child, items);
            }
        }

        return items;
    }

    protected void addSelectItem(UISelectItem uiItem, List<SelectItem> items) {

        boolean isRendered = uiItem.isRendered();
        if (!isRendered) {
            items.add(null);
            return;
        }

        Object value = uiItem.getValue();
        SelectItem item;

        if (value instanceof SelectItem) {
            item = (SelectItem) value;
        } else {
            Object itemValue = uiItem.getItemValue();
            String itemLabel = uiItem.getItemLabel();
            // JSF throws a null pointer exception for null values and labels,
            // which is a serious problem at design-time.
            item = new SelectItem(itemValue == null ? "" : itemValue,
                    itemLabel == null ? "" : itemLabel, uiItem.getItemDescription(), uiItem.isItemDisabled());
        }

        items.add(item);
    }

    @SuppressWarnings("unchecked")
    protected void addSelectItems(UISelectItems uiItems, List<SelectItem> items) {

        boolean isRendered = uiItems.isRendered();
        if (!isRendered) {
            items.add(null);
            return;
        }

        Object value = uiItems.getValue();
        if (value instanceof SelectItem) {
            items.add((SelectItem) value);
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            for (int i = 0; i < array.length; i++) {
                // TODO test - this section is untested
                if (array[i] instanceof SelectItemGroup) {
                    resolveAndAddItems((SelectItemGroup) array[i], items);
                } else {
                    items.add((SelectItem) array[i]);
                }
            }
        } else if (value instanceof Collection) {
            Iterator<SelectItem> iter = ((Collection<SelectItem>) value).iterator();
            SelectItem item;
            while (iter.hasNext()) {
                item = iter.next();
                if (item instanceof SelectItemGroup) {
                    resolveAndAddItems((SelectItemGroup) item, items);
                } else {
                    items.add(item);
                }
            }
        } else if (value instanceof Map) {
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
                Object label = entry.getKey();
                SelectItem item = new SelectItem(entry.getValue(),
                        label == null ? (String) null : label.toString());
                // TODO test - this section is untested
                if (item instanceof SelectItemGroup) {
                    resolveAndAddItems((SelectItemGroup) item, items);
                } else {
                    items.add(item);
                }
            }
        }
    }

    protected void resolveAndAddItems(SelectItemGroup group, List<SelectItem> items) {
        for (SelectItem item : group.getSelectItems()) {
            if (item instanceof SelectItemGroup) {
                resolveAndAddItems((SelectItemGroup) item, items);
            } else {
                items.add(item);
            }
        }
    }
}
