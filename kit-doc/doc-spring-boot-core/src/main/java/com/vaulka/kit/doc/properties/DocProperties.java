package com.vaulka.kit.doc.properties;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.utils.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 接口文档参数配置
 *
 * @author Vaulka
 **/
@Getter
@Setter
@ConfigurationProperties(DocProperties.PREFIX)
public class DocProperties {

    /**
     * 前缀
     */
    public static final String PREFIX = "kit.doc";

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 文档作者
     */
    private String author = "";

    /**
     * 文档标题
     */
    private String title = "API Docs";

    /**
     * 文档描述
     */
    private String description = "";

    /**
     * 文档版本号
     */
    private String version = "";

    /**
     * 请求参数信息
     * <p>
     * key: 鉴权参数存放位置
     * value：参数名称列表
     */
    private Map<SecurityScheme.In, List<String>> requestParameters = Collections.emptyMap();

    /**
     * 组别列表
     * <p>
     * 未填写则会有一个默认分组
     */
    private List<GroupOpenApi> groups = Collections.emptyList();

    /**
     * {@link SpringDocConfigProperties.GroupConfig}
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    public static class GroupOpenApi {

        /**
         * The Display name.
         */
        private String displayName;

        /**
         * The Group.
         */
        private String group;

        /**
         * The Paths to match.
         */
        private List<String> pathsToMatch = Collections.emptyList();

        /**
         * The Packages to scan.
         */
        private List<String> packagesToScan = new ArrayList<>();

        /**
         * The Packages to exclude.
         */
        private List<String> packagesToExclude = new ArrayList<>();

        /**
         * The Paths to exclude.
         */
        private List<String> pathsToExclude = new ArrayList<>();

        /**
         * The Produces to match.
         */
        private List<String> producesToMatch = new ArrayList<>();

        /**
         * The Headers to match.
         */
        private List<String> headersToMatch = new ArrayList<>();

        /**
         * The Consumes to match.
         */
        private List<String> consumesToMatch = new ArrayList<>();

        /**
         * build GroupedOpenApi
         *
         * @param enabled 是否启用
         * @return GroupedOpenApi
         */
        public GroupedOpenApi build(boolean enabled) {
            GroupedOpenApi.Builder builder = GroupedOpenApi.builder()
                    .displayName(StringUtils.defaultIfEmpty(displayName, group))
                    .group(Objects.requireNonNull(group, Constants.GROUP_NAME_NOT_NULL));
            if (!enabled) {
                return builder.addOpenApiMethodFilter(method -> false).build();
            }
            return builder.pathsToMatch(pathsToMatch.toArray(new String[0]))
                    .packagesToScan(packagesToScan.toArray(new String[0]))
                    .packagesToExclude(packagesToExclude.toArray(new String[0]))
                    .pathsToExclude(pathsToExclude.toArray(new String[0]))
                    .producesToMatch(producesToMatch.toArray(new String[0]))
                    .headersToMatch(headersToMatch.toArray(new String[0]))
                    .consumesToMatch(consumesToMatch.toArray(new String[0]))
                    .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Operation.class))
                    .build();
        }

    }

}