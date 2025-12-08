package com.digitilius.jmix.addon.component;

import io.jmix.flowui.sys.registration.ComponentRegistration;
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentRegistrationConfiguration {

    @Bean
    public ComponentRegistration slider() {
        return ComponentRegistrationBuilder.create(ActiveOnlyCheckbox.class)
                .withComponentLoader("activeOnlyCheckbox", ActiveOnlyCheckboxLoader.class)
                .build();
    }

}