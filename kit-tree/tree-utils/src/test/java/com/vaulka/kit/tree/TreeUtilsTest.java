package com.vaulka.kit.tree;

import com.vaulka.kit.tree.model.TreeNode;
import com.vaulka.kit.tree.utils.TreeUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaulka
 */
public class TreeUtilsTest {

    public static class User extends TreeNode<User, Long> {

        public User(Long id, Long parentId, String name, Integer sex) {
            super(id, parentId);
            this.name = name;
            this.sex = sex;
        }

        public User() {
        }

        private String name;

        private Integer sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }


    }

    @Test
    public void tree() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, 0L, "a", 1));
        users.add(new User(2L, 0L, "b", 2));
        users.add(new User(3L, 1L, "c", 3));
        users.add(new User(4L, 1L, "d", 4));
        users.add(new User(5L, 2L, "e", 5));
        users.add(new User(6L, 2L, "f", 6));
        users.add(new User(7L, 5L, "g", 7));
        users.add(new User(8L, 9L, "h", 8));
        List<User> treeNodes = new TreeUtils<User, Long>().buildNode(users, 0L);
        System.out.println(treeNodes.toString());
    }

}
