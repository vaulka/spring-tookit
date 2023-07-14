package com.vaulka.kit.minio.utils;

import com.vaulka.kit.minio.enums.RenameType;
import com.vaulka.kit.minio.model.UploadInfo;
import io.minio.messages.Part;
import org.junit.jupiter.api.Test;

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
public class RandomMinioUtilsTest {

    /**
     * endpoint
     */
    private static final String endpoint = "http://localhost:9000";

    /**
     * bucket
     */
    private static final String bucket = "vaulka";

    /**
     * accessKey
     */
    private static final String accessKey = "admin";

    /**
     * secretKey
     */
    private static final String secretKey = "12345678";

    /**
     * 文件前缀
     */
    private static final String filePrefix = "/project-file/";

    /**
     * 是否随机文件名称
     */
    private static final RenameType renameType = RenameType.TIMESTAMP;

    MinioUtils utils = new MinioUtils(endpoint, bucket, accessKey, secretKey, filePrefix, renameType);

    /**
     * 文件上传
     */
    @Test
    public void upload() throws IOException, InterruptedException {
        String fileName = "avatar.jpg";
        try (FileInputStream inputStream = new FileInputStream("src/test/resources/" + fileName)) {
            UploadInfo info = utils.upload(fileName, inputStream);
            assert info.getName().equals(fileName);
            assert info.getPath().startsWith(filePrefix);
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            URI uri = URI.create(endpoint + "/" + bucket + info.getPath());
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
        UploadInfo info = utils.initPartUpload(fileName);
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
                utils.partUpload(info.getUploadId(), i + 1, curPartSize, info.getPath(), inputStream);
            }
        }
        List<Part> parts = utils.listPart(info.getUploadId(), info.getPath());
        utils.completePartUpload(info.getUploadId(), info.getPath(), parts);
        assert info.getName().equals(fileName);
        assert info.getPath().startsWith(filePrefix);
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        URI uri = URI.create(endpoint + "/" + bucket + info.getPath());
        HttpResponse<String> response = client.send(HttpRequest.newBuilder().GET().uri(uri).build(), HttpResponse.BodyHandlers.ofString());
        assert response.statusCode() == 200;
    }

}
