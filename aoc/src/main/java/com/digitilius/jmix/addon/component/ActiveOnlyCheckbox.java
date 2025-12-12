package com.digitilius.jmix.addon.component;

import com.digitilius.jmix.addon.ApplicationContextProvider;
import io.jmix.core.Messages;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.model.CollectionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveOnlyCheckbox extends JmixCheckbox {

    private static final Logger log = LoggerFactory.getLogger(ActiveOnlyCheckbox.class);

    private CollectionLoader<?> loader;
    private String dataLoader;

    private String activeField = "active";
    private String orderByField = "name";
    private OrderDirection orderDirection = OrderDirection.ASC;
    private boolean initialValue = false;

    private boolean listenerAttached = false;

    public void setLoader(CollectionLoader<?> loader) {
        this.loader = loader;
    }

    public void setDataLoader(String dataLoader) {
        this.dataLoader = dataLoader;
    }

    public void setActiveField(String activeField) {
        this.activeField = activeField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public void setInitialValue(boolean initialValue) {
        this.initialValue = initialValue;
    }

    public String getDataLoader() {
        return dataLoader;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = OrderDirection.valueOf(orderDirection);
    }


    @Override
    public void initComponent() {
        super.initComponent();

        if (!listenerAttached) {
            addValueChangeListener(e -> {
                Boolean v = e.getValue();
                applyFilter(v != null && v);
            });
            listenerAttached = true;
        }
        getElement().getStyle().set("align-self", "center");
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        if (getLabel() == null) {
            Messages messages = ApplicationContextProvider.getApplicationContext().getBean(Messages.class);
            String label = messages.getMessage("activeOnly.label");
            setLabel(label);
        }
        setValue(initialValue);
    }

    private void applyFilter(boolean activeOnly) {
        if (loader == null) {
            log.warn("applyFilter called but loader is not set");
            return;
        }

        MetaClass metaClass = loader.getContainer().getEntityMetaClass();
        String entityName = metaClass.getName();

        StringBuilder sb = new StringBuilder("select e from ")
                .append(entityName)
                .append(" e");

        if (activeOnly) {
            sb.append(" where e.").append(activeField).append(" = :").append(activeField);
        }

        if (orderByField != null) {
            sb.append(" order by e.").append(orderByField);
            if (orderDirection != null) {
                sb.append(' ').append(orderDirection);
            }
        }

        String query = sb.toString();

        if (activeOnly) {
            loader.setParameter(activeField, true);
        } else {
            try {
                loader.removeParameter(activeField);
            } catch (Exception ignored) {
            }
        }

        loader.setQuery(query);
        try {
            loader.load();
        } catch (Exception ex) {
            log.error("Failed to load data for loader (query: {}). Exception: {}", query, ex.getMessage(), ex);
        }
    }

}
