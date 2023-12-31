package com.vaulka.kit.web;

import com.vaulka.kit.web.autoconfigure.WebAutoConfiguration;
import com.vaulka.kit.web.controller.Controller;
import com.vaulka.kit.web.controller.ResponseController;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;

/**
 * @author Vaulka
 */
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource("classpath:application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , classes = {WebAutoConfiguration.class, Controller.class, ResponseController.class})
public class WebSpringTest {

    @Resource
    private MockMvc mockMvc;

    /**
     * 测试接口
     *
     * @throws Exception 异常
     */
    @Test
    public void api() throws Exception {
        // test global response
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/response/get")
                        .queryParam("date", "2023-06-12 11:11:11")
                        .queryParam("localDate", "2023-06-12")
                        .queryParam("localTime", "11:11:11")
                        .queryParam("localDateTime", "2023-06-12 11:11:11")
                        .queryParam("message", "vaulka")
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"code\":0,\"message\":\"成功\",\"data\":{\"date\":\"2023-06-12 11:11:11\",\"localDate\":\"2023-06-12\",\"localTime\":\"11:11:11\",\"localDateTime\":\"2023-06-12 11:11:11\",\"message\":\"vaulka\"}}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/response/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"code\":0,\"message\":\"成功\",\"data\":{\"date\":\"2023-06-13 10:10:10\",\"localDate\":\"2023-06-13\",\"localTime\":\"10:10:10\",\"localDateTime\":\"2023-06-13 10:10:10\",\"message\":\"vaulka\"}}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/response/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"code\":0,\"message\":\"成功\",\"data\":{\"date\":\"2023-06-13 10:10:10\",\"localDate\":\"2023-06-13\",\"localTime\":\"10:10:10\",\"localDateTime\":\"2023-06-13 10:10:10\",\"message\":\"vaulka\"}}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/response/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"code\":0,\"message\":\"成功\",\"data\":{\"date\":\"2023-06-13 10:10:10\",\"localDate\":\"2023-06-13\",\"localTime\":\"10:10:10\",\"localDateTime\":\"2023-06-13 10:10:10\",\"message\":\"vaulka\"}}");

        // test response
        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/get")
                        .queryParam("date", "2023-06-12 11:11:11")
                        .queryParam("localDate", "2023-06-12")
                        .queryParam("localTime", "11:11:11")
                        .queryParam("localDateTime", "2023-06-12 11:11:11")
                        .queryParam("message", "vaulka")
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"date\":\"2023-06-12 11:11:11\",\"localDate\":\"2023-06-12\",\"localTime\":\"11:11:11\",\"localDateTime\":\"2023-06-12 11:11:11\",\"message\":\"vaulka\"}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"date\":\"2023-06-13 10:10:10\",\"localDate\":\"2023-06-13\",\"localTime\":\"10:10:10\",\"localDateTime\":\"2023-06-13 10:10:10\",\"message\":\"vaulka\"}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"date\":\"2023-06-13 10:10:10\",\"localDate\":\"2023-06-13\",\"localTime\":\"10:10:10\",\"localDateTime\":\"2023-06-13 10:10:10\",\"message\":\"vaulka\"}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"date\":\"2023-06-13 10:10:10\",\"localDate\":\"2023-06-13\",\"localTime\":\"10:10:10\",\"localDateTime\":\"2023-06-13 10:10:10\",\"message\":\"vaulka\"}");

        resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/null/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "date": "2023-06-13 10:10:10",
                                        "localDate": "2023-06-13",
                                        "localTime": "10:10:10",
                                        "localDateTime": "2023-06-13 10:10:10",
                                        "message": "vaulka"
                                    }
                                """)
                )
                .andDo(MockMvcResultHandlers.print());
        assert resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8)
                .equals("{\"code\":105,\"message\":\"/null/delete 接口不存在\"}");
    }

}
