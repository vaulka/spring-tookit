package com.vaulka.kit.minio.utils;

import com.google.common.collect.HashMultimap;
import com.vaulka.kit.minio.client.CustomMinioAsyncClient;
import com.vaulka.kit.minio.enums.RenameType;
import com.vaulka.kit.minio.exception.MinioException;
import com.vaulka.kit.minio.model.UploadInfo;
import io.minio.BucketExistsArgs;
import io.minio.CreateMultipartUploadResponse;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.ListPartsResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioAsyncClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.SetBucketPolicyArgs;
import io.minio.UploadPartResponse;
import io.minio.messages.Item;
import io.minio.messages.Part;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * MinIO 工具
 *
 * @author Vaulka
 */
public class MinioUtils {

    /**
     * endpoint
     */
    private final String endpoint;

    /**
     * bucket
     */
    private final String bucket;

    /**
     * accessKey
     */
    private final String accessKey;

    /**
     * secretKey
     */
    private final String secretKey;

    /**
     * 文件前缀
     */
    private final String filePrefix;

    /**
     * 重命名类型
     */
    private final RenameType renameType;

    /**
     * MinIO client
     */
    private final CustomMinioAsyncClient client;

    /**
     * 初始化 MinIO Client
     *
     * @param endpoint   endpoint
     * @param bucket     bucket
     * @param accessKey  accessKey
     * @param secretKey  secretKey
     * @param filePrefix 文件前缀
     * @param renameType 重命名类型
     */
    public MinioUtils(String endpoint, String bucket,
                      String accessKey, String secretKey,
                      String filePrefix, RenameType renameType) {
        this.endpoint = endpoint;
        this.bucket = bucket;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.filePrefix = filePrefix;
        this.renameType = renameType;
        client = this.getClient();
        this.createBucket();
    }

    /**
     * 创建 client
     *
     * @return 创建 client
     */
    private CustomMinioAsyncClient getClient() {
        return new CustomMinioAsyncClient(MinioAsyncClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build());
    }

    /**
     * bucket 占位符
     */
    private static final String BUCKET_PLACEHOLDER = "{0}";

    /**
     * 公共读策略
     *
     * @see <a href="https://docs.min.io/docs/java-client-api-reference.html#setBucketPolicy">setBucketPolicy</a>
     */
    private static final String BUCKET_POLICY_BY_PUBLIC_READ = "{\n" +
            "    \"Version\": \"2012-10-17\",\n" +
            "    \"Statement\": [\n" +
            "        {\n" +
            "            \"Effect\": \"Allow\",\n" +
            "            \"Principal\": \"*\",\n" +
            "            \"Action\": [\n" +
            "                \"s3:GetBucketLocation\",\n" +
            "                \"s3:ListBucket\"\n" +
            "            ],\n" +
            "            \"Resource\": [\n" +
            "                \"arn:aws:s3:::" + BUCKET_PLACEHOLDER + "\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"Effect\": \"Allow\",\n" +
            "            \"Principal\": \"*\",\n" +
            "            \"Action\": [\n" +
            "                \"s3:GetObject\"\n" +
            "            ],\n" +
            "            \"Resource\": [\n" +
            "                \"arn:aws:s3:::" + BUCKET_PLACEHOLDER + "/*\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    /***
     *  创建 bucket（如果不存在，则自动创建）
     *
     */
    private void createBucket() {
        // 判断 bucket 是否存在
        boolean isExists;
        try {
            isExists = client.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build()).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
        if (isExists) {
            return;
        }
        // 不存在则创建
        try {
            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build()).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
        // 设置 bucket 策略为公共读
        try {
            client.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(bucket)
                    .config(BUCKET_POLICY_BY_PUBLIC_READ.replace(BUCKET_PLACEHOLDER, bucket))
                    .build()).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param fileName    文件名称
     * @param inputStream input 流
     * @return 文件信息
     */
    public UploadInfo upload(String fileName, InputStream inputStream) {
        return this.upload(fileName, "application/octet-stream", inputStream);
    }

    /**
     * 文件上传
     *
     * @param fileName    文件名称
     * @param contentType 文件类型
     * @param inputStream input 流
     * @return 文件信息
     */
    public UploadInfo upload(String fileName, String contentType, InputStream inputStream) {
        UploadInfo info = new UploadInfo();
        info.setName(fileName);
        String filePath = filePrefix + renameType.rename(fileName);
        info.setPath(filePath);
        try (inputStream) {
            client.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(contentType)
                    .build()).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
        return info;
    }

    /**
     * 获取分片上传事件ID
     *
     * @param fileName 文件名称
     * @return 文件信息
     */
    public UploadInfo initPartUpload(String fileName) {
        return this.initPartUpload(fileName, "application/octet-stream");
    }

    /**
     * 获取分片上传事件ID
     *
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @return 文件信息
     */
    public UploadInfo initPartUpload(String fileName, String contentType) {
        UploadInfo info = new UploadInfo();
        info.setName(fileName);
        String filePath = filePrefix + renameType.rename(fileName);
        info.setPath(filePath);
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.put("Content-Type", contentType);
        CreateMultipartUploadResponse response;
        try {
            response = client.initPartUpload(bucket, null, filePath, headers, null).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
        String uploadId = response.result().uploadId();
        info.setUploadId(uploadId);
        return info;
    }

    /**
     * 分片上传
     * <p>
     * 需要注意，最小分片大小需要 5MB，不然合并会报错，相关 <a href="https://github.com/minio/minio/issues/11076">issues</a>
     * <p>
     * 也就意味着，适用场景为文件 &gt;= 10MB，使用分片上传更合适。（10MB 以下也只能分一片，不如用简单上传，减少接口请求次数）
     *
     * @param uploadId    分片上传事件ID
     * @param partNumber  当前分片数
     * @param partSize    当前分片文件大小，单位为字节，譬如：10MB = 10 * 1024 * 1024L
     * @param filePath    文件路径
     * @param inputStream input 流
     * @return 分片信息
     */
    public Part partUpload(String uploadId, int partNumber, int partSize, String filePath, InputStream inputStream) {
        return this.partUpload(uploadId, partNumber, partSize, filePath, new BufferedInputStream(inputStream));
    }

    /**
     * 分片上传
     * <p>
     * 需要注意，最小分片大小需要 5MB，不然合并会报错，相关 <a href="https://github.com/minio/minio/issues/11076">issues</a>
     * <p>
     * 也就意味着，适用场景为文件 &gt;= 10MB，使用分片上传更合适。（10MB 以下也只能分一片，不如用简单上传，减少接口请求次数）
     *
     * @param uploadId    分片上传事件ID
     * @param partNumber  当前分片数
     * @param partSize    当前分片文件大小，单位为字节，譬如：10MB = 10 * 1024 * 1024L
     * @param filePath    文件路径
     * @param inputStream input 流
     * @return 分片信息
     */
    public Part partUpload(String uploadId, int partNumber, int partSize, String filePath, BufferedInputStream inputStream) {
        UploadPartResponse response;
        try (inputStream) {
            response = client.partUpload(bucket, null, filePath, inputStream, partSize, uploadId, partNumber, null, null).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
        return new Part(response.partNumber(), response.etag());
    }

    /**
     * 查询分片信息
     *
     * @param uploadId 分片上传事件ID
     * @param filePath 文件路径
     * @return 分片信息列表
     */
    public List<Part> listPart(String uploadId, String filePath) {
        ListPartsResponse response;
        try {
            response = client.listPart(bucket, null, filePath, null, null, uploadId, null, null).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
        return response.result().partList();
    }

    /**
     * 合并分片上传
     *
     * @param uploadId 分片上传事件ID
     * @param filePath 文件路径
     * @param parts    分片信息列表
     */
    public void completePartUpload(String uploadId, String filePath, List<Part> parts) {
        this.completePartUpload(uploadId, filePath, parts.toArray(new Part[0]));
    }

    /**
     * 合并分片上传
     *
     * @param uploadId 分片上传事件ID
     * @param filePath 文件路径
     * @param parts    分片信息列表
     */
    public void completePartUpload(String uploadId, String filePath, Part[] parts) {
        try {
            client.completePartUpload(bucket, null, filePath, uploadId, parts,
                    null, null);
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return inputStream
     */
    public InputStream download(String filePath) {
        try {
            return client.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build()).get();
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage(), e);
        }
    }


    /**
     * 获取文件列表
     *
     * @param prefix 文件夹前缀
     * @return 文件列表
     */
    public List<String> list(String prefix) {
        Iterable<Result<Item>> iterable = client.listObjects(ListObjectsArgs.builder()
                .bucket(bucket)
                .prefix(prefix)
                .recursive(true)
                .build());
        List<String> fileNames = new ArrayList<>();
        for (Result<Item> result : iterable) {
            Item item;
            try {
                item = result.get();
            } catch (Exception e) {
                throw new MinioException(e.getLocalizedMessage(), e);
            }
            fileNames.add(item.objectName());
        }
        return fileNames;
    }

}
