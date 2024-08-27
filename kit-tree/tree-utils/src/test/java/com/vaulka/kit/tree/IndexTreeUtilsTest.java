package com.vaulka.kit.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vaulka.kit.tree.model.StringUser;
import com.vaulka.kit.tree.utils.BuildTreeUtils;
import com.vaulka.kit.tree.utils.IndexTreeUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaulka
 */
public class IndexTreeUtilsTest {

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
     * 测试 Long 树状结构 删除无效数据树状节点
     *
     * @throws JsonProcessingException JSON 处理异常
     */
    @Test
    public void removeInvalidNode() throws JsonProcessingException {
        List<StringUser> stringUsers = new ArrayList<>();
        stringUsers.add(new StringUser("1", "0", "a", 1));
        stringUsers.add(new StringUser("2", "0", "b", 2));
        stringUsers.add(new StringUser("3", "1", "c", 3));
        stringUsers.add(new StringUser("4", "1", "d", 4));
        stringUsers.add(new StringUser("5", "2", "e", 5));
        stringUsers.add(new StringUser("6", "2", "f", 6));
        stringUsers.add(new StringUser("7", "5", "g", 7));
        stringUsers.add(new StringUser("8", "9", "h", 8));
        stringUsers.add(new StringUser("9", "7", "i", 9));
        stringUsers.add(new StringUser("10", "9", "j", 10));
        BuildTreeUtils<StringUser, String> buildTreeUtils = new BuildTreeUtils<>(StringUser::getId, StringUser::getParentId, StringUser::setChildren,
                StringUser::getRootIds, StringUser::setRootIds);
        IndexTreeUtils<StringUser, String> indexTreeUtils = new IndexTreeUtils<>(StringUser::getId, StringUser::getChildren);
        List<StringUser> treeNodes = buildTreeUtils.buildNode(stringUsers, "0");
        List<StringUser> path = indexTreeUtils.findPath(treeNodes, "9");
        String json = MAPPER.writeValueAsString(path);
        System.out.println(json);
        assert json.equals("[{\"id\":\"2\",\"parentId\":\"0\",\"children\":[{\"id\":\"5\",\"parentId\":\"2\",\"children\":[{\"id\":\"7\",\"parentId\":\"5\",\"children\":[{\"id\":\"9\",\"parentId\":\"7\",\"children\":[{\"id\":\"8\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"8\"],\"name\":\"h\",\"sex\":8},{\"id\":\"10\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"10\"],\"name\":\"j\",\"sex\":10}],\"rootIds\":[\"2\",\"5\",\"7\",\"9\"],\"name\":\"i\",\"sex\":9}],\"rootIds\":[\"2\",\"5\",\"7\"],\"name\":\"g\",\"sex\":7}],\"rootIds\":[\"2\",\"5\"],\"name\":\"e\",\"sex\":5},{\"id\":\"6\",\"parentId\":\"2\",\"children\":[],\"rootIds\":[\"2\",\"6\"],\"name\":\"f\",\"sex\":6}],\"rootIds\":[\"2\"],\"name\":\"b\",\"sex\":2},{\"id\":\"5\",\"parentId\":\"2\",\"children\":[{\"id\":\"7\",\"parentId\":\"5\",\"children\":[{\"id\":\"9\",\"parentId\":\"7\",\"children\":[{\"id\":\"8\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"8\"],\"name\":\"h\",\"sex\":8},{\"id\":\"10\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"10\"],\"name\":\"j\",\"sex\":10}],\"rootIds\":[\"2\",\"5\",\"7\",\"9\"],\"name\":\"i\",\"sex\":9}],\"rootIds\":[\"2\",\"5\",\"7\"],\"name\":\"g\",\"sex\":7}],\"rootIds\":[\"2\",\"5\"],\"name\":\"e\",\"sex\":5},{\"id\":\"7\",\"parentId\":\"5\",\"children\":[{\"id\":\"9\",\"parentId\":\"7\",\"children\":[{\"id\":\"8\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"8\"],\"name\":\"h\",\"sex\":8},{\"id\":\"10\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"10\"],\"name\":\"j\",\"sex\":10}],\"rootIds\":[\"2\",\"5\",\"7\",\"9\"],\"name\":\"i\",\"sex\":9}],\"rootIds\":[\"2\",\"5\",\"7\"],\"name\":\"g\",\"sex\":7},{\"id\":\"9\",\"parentId\":\"7\",\"children\":[{\"id\":\"8\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"8\"],\"name\":\"h\",\"sex\":8},{\"id\":\"10\",\"parentId\":\"9\",\"children\":[],\"rootIds\":[\"2\",\"5\",\"7\",\"9\",\"10\"],\"name\":\"j\",\"sex\":10}],\"rootIds\":[\"2\",\"5\",\"7\",\"9\"],\"name\":\"i\",\"sex\":9}]");
    }

}
