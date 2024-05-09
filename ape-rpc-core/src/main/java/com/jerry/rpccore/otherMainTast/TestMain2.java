package com.jerry.rpccore.otherMainTast;

import java.util.*;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/11 16:38
 * @注释 一致性hash算法
 */
public class TestMain2 {

    public static void main(String[] args) {
        List<String> nodes = Arrays.asList("Node1", "Node2", "Node3", "Node4", "Node5");
        ConsistentHashing consistentHashing = new ConsistentHashing(100, nodes);

        String key = "someKey";
        String node = consistentHashing.get(key);
        System.out.println("Node for key " + key + " is " + node);

        // Remove a node
        consistentHashing.remove("Node2");
        node = consistentHashing.get(key);
        System.out.println("Node for key " + key + " after removing Node2 is " + node);
    }

}

/***
 * 一致性hash算法
 */
class ConsistentHashing {
    private final int numberOfReplicas;
    private final SortedMap<Integer, String> circle = new TreeMap<>();

    public ConsistentHashing(int numberOfReplicas, List<String> nodes) {
        this.numberOfReplicas = numberOfReplicas;
        for (String node : nodes) {
            add(node);
        }
    }

    public void add(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hash(node + i), node);
        }
    }

    public void remove(String node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hash(node + i));
        }
    }

    public String get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, String> tailMap = circle.tailMap(hash); //返回包含了大于或等于给定键的所有键-值映射。
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    private int hash(Object key) {
        return Objects.hash(key);
    }

}