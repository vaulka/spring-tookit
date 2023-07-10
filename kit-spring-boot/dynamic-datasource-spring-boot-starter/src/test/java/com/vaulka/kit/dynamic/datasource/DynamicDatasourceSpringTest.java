package com.vaulka.kit.dynamic.datasource;

import com.vaulka.kit.dynamic.datasource.autoconfigure.DynamicDatasourceAutoConfiguration;
import com.vaulka.kit.dynamic.datasource.controller.DynamicDatasourceController;
import com.vaulka.kit.dynamic.datasource.mapper.UserMapper;
import com.vaulka.kit.dynamic.datasource.service.UserServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Vaulka
 */
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource("classpath:application.yml")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//        , classes = {
//        DynamicDatasourceAutoConfiguration.class,
//        DynamicDatasourceController.class,
//        UserMapper.class,
//        UserServiceImpl.class
//})
public class DynamicDatasourceSpringTest {

    @Resource
    private MockMvc mockMvc;

    /**
     * 测试接口
     *
     * @throws Exception 异常
     */
//    @Test
    public void api() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/response/xxx"))
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("[{\"id\":1,\"message\":\"x\"},{\"id\":2,\"message\":\"x\"},{\"id\":3,\"message\":\"x\"}]");

        List<String> response = List.of("[{\"id\":1,\"message\":\"y\"},{\"id\":2,\"message\":\"y\"},{\"id\":3,\"message\":\"y\"}]"
                , "[{\"id\":1,\"message\":\"z\"},{\"id\":2,\"message\":\"z\"},{\"id\":3,\"message\":\"z\"}]"
                , "[{\"id\":1,\"message\":\"x\"},{\"id\":2,\"message\":\"y\"},{\"id\":3,\"message\":\"z\"}]");

        Stream.iterate(0, i -> i + 1).limit(500)
                .toList().parallelStream()
                .forEach(d -> {
                    try {
                        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/api/response/yyy"))
                                .andDo(MockMvcResultHandlers.print());
                        assert response.contains(actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/response/zzz"))
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("[{\"id\":1,\"message\":\"y\"},{\"id\":2,\"message\":\"y\"},{\"id\":3,\"message\":\"y\"}]");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/response/xyz"))
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("[{\"id\":1,\"message\":\"z\"},{\"id\":2,\"message\":\"z\"},{\"id\":3,\"message\":\"z\"}]");

    }

}
