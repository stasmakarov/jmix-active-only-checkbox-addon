package com.digitilius.jmix.addon.kit;

import com.digitilius.jmix.addon.component.ActiveOnlyCheckbox;
import io.jmix.flowui.kit.meta.StudioComponent;
import io.jmix.flowui.kit.meta.StudioProperty;
import io.jmix.flowui.kit.meta.StudioPropertyType;
import io.jmix.flowui.kit.meta.StudioUiKit;

@StudioUiKit
public interface StudioComponents {
    @StudioComponent(
        name = "ActiveOnlyCheckbox",
        classFqn = "com.digitilius.jmix.addon.component.ActiveOnlyCheckbox",
        category = "Components",
        xmlElement = "activeOnlyCheckbox",
        xmlns = "http://digitilius.com/schema/active-only-checkbox",
        xmlnsAlias = "act",
        icon = "com/digitilius/jmix/aoc/kit/meta/icon/component/checkbox.svg",
        properties = {
            @StudioProperty(xmlAttribute = "id", type = StudioPropertyType.COMPONENT_ID),
            @StudioProperty(xmlAttribute = "label", type = StudioPropertyType.LOCALIZED_STRING),
            @StudioProperty(xmlAttribute = "active", type = StudioPropertyType.STRING),
            @StudioProperty(xmlAttribute = "orderBy", type = StudioPropertyType.STRING),
            @StudioProperty(xmlAttribute = "loaderId", type = StudioPropertyType.STRING),
            @StudioProperty(xmlAttribute = "initialValue", type = StudioPropertyType.BOOLEAN),
            @StudioProperty(xmlAttribute = "enabled", type = StudioPropertyType.BOOLEAN, defaultValue = "true"),
            @StudioProperty(xmlAttribute = "visible", type = StudioPropertyType.BOOLEAN, defaultValue = "true"),
            @StudioProperty(xmlAttribute = "classNames", type = StudioPropertyType.VALUES_LIST),
            @StudioProperty(xmlAttribute = "css", type = StudioPropertyType.STRING),
            @StudioProperty(xmlAttribute = "helperText", type = StudioPropertyType.LOCALIZED_STRING),

    })
    ActiveOnlyCheckbox activeOnlyCheckbox();
}
