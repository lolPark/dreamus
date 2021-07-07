package com.dreamus.lolpark.purchase.config;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@TestConfiguration
public class MvcConfig implements MockMvcBuilderCustomizer {

    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.addFilter(new CharacterEncodingFilter("UTF-8", true));
    }
}
