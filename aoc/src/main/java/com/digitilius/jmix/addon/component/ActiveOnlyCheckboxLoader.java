package com.digitilius.jmix.addon.component;

import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataLoader;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import io.jmix.flowui.xml.layout.support.DataLoaderSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

public class ActiveOnlyCheckboxLoader extends AbstractComponentLoader<ActiveOnlyCheckbox> {
    protected DataLoaderSupport dataLoaderSupport;

    @Override
    @NonNull
    protected ActiveOnlyCheckbox createComponent() {
        return factory.create(ActiveOnlyCheckbox.class);
    }

    @Override
    public void loadComponent() {

        getDataLoaderSupport().loadData(resultComponent, element);

        loadString(element, "activeField", resultComponent::setActiveField);
        loadString(element, "orderByField", resultComponent::setOrderByField);
        loadString(element, "orderDirection", resultComponent::setOrderDirection);
        loadBoolean(element, "initialValue", resultComponent::setInitialValue);
        loadString(element, "dataLoader", resultComponent::setDataLoader);

        loadLoader(resultComponent.getDataLoader());
    }

    protected DataLoaderSupport getDataLoaderSupport() {
        if (dataLoaderSupport == null) {
            dataLoaderSupport = applicationContext.getBean(DataLoaderSupport.class, context);
        }
        return dataLoaderSupport;
    }

    protected void loadLoader(@NonNull String loaderId) {
        DataLoader loader = context.getDataHolder().getLoader(loaderId);
        if (loader instanceof CollectionLoader<?>) {
            resultComponent.setLoader((CollectionLoader<?>) loader);
        } else {
            throw new GuiDevelopmentException("Not supported loader type: %", loaderId);
        }
    }

}
