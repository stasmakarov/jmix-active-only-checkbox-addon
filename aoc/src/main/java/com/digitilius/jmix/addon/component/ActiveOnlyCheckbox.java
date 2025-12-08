package com.digitilius.jmix.addon.component;

import io.jmix.core.Messages;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class ActiveOnlyCheckbox extends JmixCheckbox {

    private static final Logger log = LoggerFactory.getLogger(ActiveOnlyCheckbox.class);

    @Autowired
    private Messages messages;

    private CollectionLoader<?> loader;
    private String loaderId;

    private String active = "active";
    private String orderBy = "name";
    private boolean initialValue = false;

    private boolean listenerAttached = false;

    public ActiveOnlyCheckbox() {
    }

    public void setLoader(CollectionLoader<?> loader) {
        this.loader = loader;
    }

    public void setLoaderId(String loaderId) {
        this.loaderId = loaderId;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setInitialValue(boolean initialValue) {
        this.initialValue = initialValue;
        setValue(initialValue);
    }

    public CollectionLoader<?> getLoader() {
        return loader;
    }

    public String getLoaderId() {
        return loaderId;
    }

    public String getActive() {
        return active;
    }

    public String getOrderBy() {
        return orderBy;
    }


    public void attachLoaderById(View<?> view) {
        if (loaderId == null || loaderId.isEmpty()) {
            return;
        }
        if (view == null) {
            log.warn("attachLoaderById called with null view");
            return;
        }
        try {
            Object screenData = view.getClass().getMethod("getScreenData").invoke(view);
            java.lang.reflect.Method getLoader = screenData.getClass().getMethod("getLoader", String.class);
            Object loaderObj = getLoader.invoke(screenData, loaderId);
            if (loaderObj instanceof CollectionLoader) {
                setLoader((CollectionLoader<?>) loaderObj);
                log.debug("Attached loader '{}' to ActiveOnlyCheckbox", loaderId);
            } else {
                log.warn("Object returned by getLoader('{}') is not a CollectionLoader: {}", loaderId,
                        (loaderObj != null ? loaderObj.getClass() : "null"));
            }
        } catch (NoSuchMethodException nsme) {
            log.warn("attachLoaderById: expected method missing: {}", nsme.getMessage());
        } catch (Exception ex) {
            log.warn("Error attaching loader by id '{}': {}", loaderId, ex.getMessage(), ex);
        }
    }

    @Override
    public void initComponent() {
        super.initComponent();
        Objects.requireNonNull(loader, "CollectionLoader must be set before calling init()");

        if (!listenerAttached) {
            addValueChangeListener(e -> {
                if (loader == null) {
                    log.warn("Value changed but loader is null");
                    return;
                }
                Boolean v = e.getValue();
                applyFilter(v != null && v);
            });
            listenerAttached = true;
        }

        setValue(initialValue);
        String label = messages.getMessage("com.digitilius.jmix.addon.activeOnlyCheckbox", "activeOnly");
        setLabel(label);
        applyFilter(getValue());
    }

    private void applyFilter(boolean activeOnly) {
        if (loader == null) {
            log.warn("applyFilter called but loader is not set");
            return;
        }

        MetaClass metaClass = loader.getContainer().getEntityMetaClass();
        String entityName = metaClass.getName();

        String query;
        if (activeOnly) {
            query = "select e from " + entityName +
                    " e where e." + active + " = :" + active +
                    " order by e." + orderBy;
            loader.setParameter(active, true);
        } else {
            query = "select e from " + entityName +
                    " e order by e." + orderBy;
            try {
                loader.removeParameter(active);
            } catch (Exception ignore) {
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
