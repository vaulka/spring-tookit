package com.vaulka.kit.minio.controller;

import com.vaulka.kit.minio.model.UploadInfo;
import com.vaulka.kit.minio.utils.MinioUtils;
import io.minio.messages.Part;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Vaulka
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class Controller {

    private final MinioUtils minioUtils;

    @PostMapping("upload")
    public UploadInfo upload(MultipartFile file) throws IOException {
        if (file == null || file.getSize() == 0) {
            throw new RuntimeException("文件不能为空");
        }
        return minioUtils.upload(file.getOriginalFilename(), file.getInputStream());
    }

    @PostMapping("init-part-upload")
    public UploadInfo initPartUpload(String fileName) {
        return minioUtils.initPartUpload(fileName);
    }

    @PostMapping("part-upload")
    public void partUpload(String uploadId, int partNumber, String filePath, MultipartFile file) throws IOException {
        minioUtils.partUpload(uploadId, partNumber, (int) file.getSize(), filePath, file.getInputStream());
    }

    @PostMapping("complete-part-upload")
    public void completePartUpload(String uploadId, String filePath) {
        List<Part> parts = minioUtils.listPart(uploadId, filePath);
        minioUtils.completePartUpload(uploadId, filePath, parts);
    }

    @GetMapping("download")
    public void download(String fileName, String filePath, HttpServletResponse response) {
        try (InputStream inputStream = minioUtils.download(filePath)) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            inputStream.transferTo(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
