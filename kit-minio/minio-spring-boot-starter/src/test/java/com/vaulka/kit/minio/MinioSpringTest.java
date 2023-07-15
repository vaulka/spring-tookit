package com.vaulka.kit.minio;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.JsonPathExpectationsHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 安装 minio docker cmd：</hr>
 * <p>
 * docker run --name minio -d  -e MINIO_ROOT_USER=admin -e MINIO_ROOT_PASSWORD=12345678 -e MINIO_SERVER_URL=http://localhost:9000 -e MINIO_BROWSER_REDIRECT_URL=http://localhost:9001 -p 9000:9000 -p 9001:9001 minio/minio:RELEASE.2023-07-11T21-29-34Z.fips server /data --console-address ":9001"
 *
 * @author Vaulka
 */
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource("classpath:application-test.properties")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        classes = {MinioAutoConfiguration.class, Controller.class})
public class MinioSpringTest {

    @Resource
    private MockMvc mockMvc;

    /**
     * 文件上传
     */
//    @Test
    public void upload() throws Exception {
        String fileName = "avatar.jpg";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "application/octet-stream",
                new FileInputStream("src/test/resources/" + fileName));
        String content = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/upload")
                        .file(file)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fileName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andReturn().getResponse().getContentAsString();
        String path = (String) new JsonPathExpectationsHelper("$.path").evaluateJsonPath(content);

        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders.get("/api/download")
                        .queryParam("fileName", fileName)
                        .queryParam("filePath", path)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        inputStream.transferTo(new FileOutputStream("build/" + path));
    }

    /**
     * 分片上传
     */
//    @Test
    public void partUpload() throws Exception {
        String fileName = "video.mp4";
        String content = mockMvc.perform(MockMvcRequestBuilders.post("/api/init-part-upload")
                        .param("fileName", fileName)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uploadId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(fileName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").exists())
                .andReturn().getResponse().getContentAsString();
        String uploadId = (String) new JsonPathExpectationsHelper("$.uploadId").evaluateJsonPath(content);
        String path = (String) new JsonPathExpectationsHelper("$.path").evaluateJsonPath(content);

        File file = new File("src/test/resources/" + fileName);
        // 每个分片的大小，用于计算文件有多少个分片。单位为字节。这里为 1 MB。
        final int partSize = 5 * 1024 * 1024;
        int fileLength = (int) file.length();
        int partCount = fileLength / partSize;
        if (fileLength % partSize != 0) {
            partCount++;
        }
        // 遍历分片上传。
        for (int i = 0; i < partCount; i++) {
            int startPos = i * partSize;
            try (InputStream inputStream = new FileInputStream(file)) {
                // 跳过已经上传的分片。
                inputStream.skip(startPos);
                mockMvc.perform(MockMvcRequestBuilders.multipart("/api/part-upload")
                                .file(new MockMultipartFile("file", fileName, "application/octet-stream", inputStream))
                                .param("uploadId", uploadId)
                                .param("partNumber", String.valueOf(i + 1))
                                .param("filePath", path)
                        )
                        .andExpect(MockMvcResultMatchers.status().isOk());
            }
        }
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/complete-part-upload")
                        .param("uploadId", uploadId)
                        .param("filePath", path)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        byte[] bytes = mockMvc.perform(MockMvcRequestBuilders.get("/api/download")
                        .queryParam("fileName", fileName)
                        .queryParam("filePath", path)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        inputStream.transferTo(new FileOutputStream("build/" + path));
    }

}
