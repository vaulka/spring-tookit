package com.vaulka.kit.doc;

import com.vaulka.kit.doc.autoconfigure.DocAutoConfiguration;
import com.vaulka.kit.doc.controller.Controller;
import com.vaulka.kit.doc.properties.DocProperties;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.JsonPathExpectationsHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Vaulka
 */
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource("classpath:application.yml")
@TestPropertySource(properties = "kit.doc.enabled=true")
@SpringBootTest(classes = {DocAutoConfiguration.class, Controller.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnableDocSpringTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private DocProperties properties;

    /**
     * 测试接口文档
     *
     * @throws Exception 异常
     */
    @Test
    public void api() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs/swagger-config"))
                .andDo(MockMvcResultHandlers.print());
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.urls.size()").value(2));
        for (int i = 0; i < properties.getGroups().size(); i++) {
            DocProperties.GroupOpenApi openApi = properties.getGroups().get(i);
            resultActions
                    .andExpect(MockMvcResultMatchers.jsonPath("$.urls[%d].url", i).value("/v3/api-docs/" + openApi.getGroup()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.urls[%d].name", i).value(openApi.getDisplayName()));
        }
        String content = resultActions.andReturn().getResponse().getContentAsString();
        Object apiUrl = new JsonPathExpectationsHelper("$.urls[0].url").evaluateJsonPath(content);
        assert apiUrl != null;
        resultActions = mockMvc.perform(MockMvcRequestBuilders.get(apiUrl.toString()))
                .andDo(MockMvcResultHandlers.print());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.contact.name").value(properties.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.title").value(properties.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.description").value(properties.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.info.version").value(properties.getVersion()));
        for (Map.Entry<SecurityScheme.In, List<String>> entry : properties.getRequestParameters().entrySet()) {
            for (String val : entry.getValue()) {
                resultActions
                        .andExpect(MockMvcResultMatchers.jsonPath("$.components.securitySchemes.%s.in", val).value(entry.getKey().toString()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.components.securitySchemes.%s.name", val).value(val));
            }
        }
        List<String> requestParameters = properties.getRequestParameters().values().stream()
                .flatMap(Collection::stream)
                .toList();
        for (String requestParameter : requestParameters) {
            resultActions
                    .andExpect(MockMvcResultMatchers.jsonPath("$.security.[0].%s", requestParameter).isArray());

        }
        if (properties.isEnabled()) {
            resultActions
                    .andExpect(MockMvcResultMatchers.jsonPath("$.paths.*").hasJsonPath());
        }
    }

}
