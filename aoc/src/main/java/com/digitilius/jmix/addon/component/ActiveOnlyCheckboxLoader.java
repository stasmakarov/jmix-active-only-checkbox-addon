package com.digitilius.jmix.addon.component;

import io.jmix.flowui.data.SupportsValueSource;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import io.jmix.flowui.xml.layout.support.DataLoaderSupport;
import org.springframework.lang.NonNull;

public class ActiveOnlyCheckboxLoader extends AbstractComponentLoader<ActiveOnlyCheckbox>
{

    protected DataLoaderSupport dataLoaderSupport;

    @Override
    @NonNull
    protected ActiveOnlyCheckbox createComponent() {
        return factory.create(ActiveOnlyCheckbox.class);
    }

    @Override
    public void loadComponent() {

        getDataLoaderSupport().loadData(resultComponent, element);

        loadString(element, "active", resultComponent::setActive);
        loadString(element, "orderBy", resultComponent::setOrderBy);
        loadString(element, "dataLoader", resultComponent::setLoaderId);
        loadBoolean(element, "initialValue", resultComponent::setInitialValue);

        componentLoader().loadLabel(resultComponent, element);
        componentLoader().loadEnabled(resultComponent, element);
        componentLoader().loadClassNames(resultComponent, element);
        componentLoader().loadCss(resultComponent, element);
        componentLoader().loadHelperText(resultComponent, element);
    }

    protected DataLoaderSupport getDataLoaderSupport() {
        if (dataLoaderSupport == null) {
            dataLoaderSupport = applicationContext.getBean(DataLoaderSupport.class, context);
        }
        return dataLoaderSupport;
    }
}
