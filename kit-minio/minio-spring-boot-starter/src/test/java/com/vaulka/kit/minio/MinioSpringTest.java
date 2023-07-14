package com.vaulka.kit.minio;

import com.vaulka.kit.minio.autoconfigure.MinioAutoConfiguration;
import com.vaulka.kit.minio.model.UploadInfo;
import com.vaulka.kit.minio.properties.MinioProperties;
import com.vaulka.kit.minio.utils.MinioUtils;
import io.minio.messages.Part;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * @author Vaulka
 */
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = MinioAutoConfiguration.class)
public class MinioSpringTest {

    @Resource
    private MinioUtils minioUtils;


    @Resource
    private MinioProperties properties;

    /**
     * 文件上传
     */
    @Test
    public void upload() throws IOException, InterruptedException {
        String fileName = "avatar.jpg";
        try (FileInputStream inputStream = new FileInputStream("src/test/resources/" + fileName)) {
            UploadInfo info = minioUtils.upload(fileName, inputStream);
            assert info.getName().equals(fileName);
            assert info.getPath().startsWith(properties.getFilePrefix());
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            URI uri = URI.create(properties.getEndpoint() + "/" + properties.getBucket() + info.getPath());
            HttpResponse<String> response = client.send(HttpRequest.newBuilder().GET().uri(uri).build(), HttpResponse.BodyHandlers.ofString());
            assert response.statusCode() == 200;
        }
    }

    /**
     * 分片上传
     */
    @Test
    public void partUpload() throws IOException, InterruptedException {
        String fileName = "video.mp4";
        UploadInfo info = minioUtils.initPartUpload(fileName);
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
            int curPartSize = (i + 1 == partCount) ? fileLength - startPos : partSize;
            try (InputStream inputStream = new FileInputStream(file)) {
                // 跳过已经上传的分片。
                inputStream.skip(startPos);
                minioUtils.partUpload(info.getUploadId(), i + 1, curPartSize, info.getPath(), inputStream);
            }
        }
        List<Part> parts = minioUtils.listPart(info.getUploadId(), info.getPath());
        minioUtils.completePartUpload(info.getUploadId(), info.getPath(), parts);
        assert info.getName().equals(fileName);
        assert info.getPath().startsWith(properties.getFilePrefix());
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        URI uri = URI.create(properties.getEndpoint() + "/" + properties.getBucket() + info.getPath());
        HttpResponse<String> response = client.send(HttpRequest.newBuilder().GET().uri(uri).build(), HttpResponse.BodyHandlers.ofString());
        assert response.statusCode() == 200;
    }

}
