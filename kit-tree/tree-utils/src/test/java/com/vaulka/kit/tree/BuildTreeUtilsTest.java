package com.vaulka.kit.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vaulka.kit.tree.model.LongUser;
import com.vaulka.kit.tree.model.StringUser;
import com.vaulka.kit.tree.utils.BuildTreeUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaulka
 */
public class BuildTreeUtilsTest {

    /**
     * Jackson 序列化/反序列化 配置
     */
    public static final ObjectMapper MAPPER = JsonMapper.builder()
            // 只序列号非空属性
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
            // 反序列化的时候如果多了其他属性,不抛出异常
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();

    /**
     * 测试 Long 树状结构
     *
     * @throws JsonProcessingException JSON 处理异常
     */
    @Test
    public void longTree() throws JsonProcessingException {
        List<LongUser> longUsers = new ArrayList<>();
        longUsers.add(new LongUser(1L, 0L, "a", 1));
        longUsers.add(new LongUser(2L, 0L, "b", 2));
        longUsers.add(new LongUser(3L, 1L, "c", 3));
        longUsers.add(new LongUser(4L, 1L, "d", 4));
        longUsers.add(new LongUser(5L, 2L, "e", 5));
        longUsers.add(new LongUser(6L, 2L, "f", 6));
        longUsers.add(new LongUser(7L, 5L, "g", 7));
        longUsers.add(new LongUser(8L, 9L, "h", 8));
        List<LongUser> treeNodes = new BuildTreeUtils<>(LongUser::getId, LongUser::getParentId, LongUser::setChildren,
                LongUser::getRootIds, LongUser::setRootIds)
                .buildNode(longUsers, 0L);
        String json = MAPPER.writeValueAsString(treeNodes);
        System.out.println(json);
        assert json.equals("[{\"id\":1,\"parentId\":0,\"children\":[{\"id\":3,\"parentId\":1,\"children\":[],\"name\":\"c\",\"sex\":3},{\"id\":4,\"parentId\":1,\"children\":[],\"name\":\"d\",\"sex\":4}],\"name\":\"a\",\"sex\":1},{\"id\":2,\"parentId\":0,\"children\":[{\"id\":5,\"parentId\":2,\"children\":[{\"id\":7,\"parentId\":5,\"children\":[],\"name\":\"g\",\"sex\":7}],\"name\":\"e\",\"sex\":5},{\"id\":6,\"parentId\":2,\"children\":[],\"name\":\"f\",\"sex\":6}],\"name\":\"b\",\"sex\":2}]");
    }

    /**
     * 测试 Long 树状结构
     *
     * @throws JsonProcessingException JSON 处理异常
     */
    @Test
    public void stringTree() throws JsonProcessingException {
        List<StringUser> stringUsers = new ArrayList<>();
        stringUsers.add(new StringUser("1", "0", "a", 1));
        stringUsers.add(new StringUser("2", "0", "b", 2));
        stringUsers.add(new StringUser("3", "1", "c", 3));
        stringUsers.add(new StringUser("4", "1", "d", 4));
        stringUsers.add(new StringUser("5", "2", "e", 5));
        stringUsers.add(new StringUser("6", "2", "f", 6));
        stringUsers.add(new StringUser("7", "5", "g", 7));
        stringUsers.add(new StringUser("8", "9", "h", 8));
        List<StringUser> treeNodes = new BuildTreeUtils<>(StringUser::getId, StringUser::getParentId, StringUser::setChildren,
                StringUser::getRootIds, StringUser::setRootIds)
                .buildNode(stringUsers, "0");
        String json = MAPPER.writeValueAsString(treeNodes);
        System.out.println(json);
        assert json.equals("[{\"id\":\"1\",\"parentId\":\"0\",\"children\":[{\"id\":\"3\",\"parentId\":\"1\",\"children\":[],\"name\":\"c\",\"sex\":3},{\"id\":\"4\",\"parentId\":\"1\",\"children\":[],\"name\":\"d\",\"sex\":4}],\"name\":\"a\",\"sex\":1},{\"id\":\"2\",\"parentId\":\"0\",\"children\":[{\"id\":\"5\",\"parentId\":\"2\",\"children\":[{\"id\":\"7\",\"parentId\":\"5\",\"children\":[],\"name\":\"g\",\"sex\":7}],\"name\":\"e\",\"sex\":5},{\"id\":\"6\",\"parentId\":\"2\",\"children\":[],\"name\":\"f\",\"sex\":6}],\"name\":\"b\",\"sex\":2}]");
    }

}
