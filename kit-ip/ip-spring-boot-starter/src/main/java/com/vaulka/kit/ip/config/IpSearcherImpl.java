package com.vaulka.kit.ip.config;

import com.vaulka.kit.ip.model.IpInfo;
import com.vaulka.kit.ip.properties.IpProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.InputStream;

/**
 * IP 解析器
 *
 * @author Vaulka
 */
@Slf4j
@RequiredArgsConstructor
public class IpSearcherImpl implements InitializingBean, DisposableBean, IpSearcher {

    private final IpProperties properties;
    private final ResourceLoader resourceLoader;

    private Searcher searcher;

    /**
     * 初始化 IP 地址池信息
     *
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = resourceLoader.getResource(properties.getDbPath());
        try (InputStream inputStream = resource.getInputStream()) {
            searcher = Searcher.newWithBuffer(StreamUtils.copyToByteArray(inputStream));
        }
    }

    /**
     * 关闭资源
     *
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        searcher.close();
    }

    @Override
    public IpInfo parser(String ip) {
        try {
            String address = searcher.search(ip);
            return new IpInfo(address);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return new IpInfo();
    }

}
