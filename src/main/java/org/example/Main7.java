package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.swing.plaf.IconUIResource;
import java.util.function.Consumer;
import java.util.function.Function;

class Zoo{

}

public class Main7 {
    @Data
    static class Node<V extends Comparable<V>>{
        V value;
        Node<V> left;
        Node<V> right;

        public Node(V value) {
            this.value = value;
        }

        public boolean insert(V value){
            Node<V> curr = this;
            Node<V> prev = null;

            while(curr != null){
                if(value.compareTo(curr.getValue()) > 0){
                    prev = curr;
                    curr = curr.right;
                } else {
                    prev = curr;
                    curr = curr.left;
                }
            }

            Node<V> newNode = new Node<>(value);

            if(value.compareTo(prev.getValue()) > 0){
                prev.right = newNode;
            } else {
                prev.left = newNode;
            }

            return true;
        }

        public int findLeafNodes(){
            Function<Node<V>, Integer> helper  = new Function<Node<V>, Integer>() {
                @Override
                public Integer apply(Node<V> node) {
                    if(node == null){
                        return 0;
                    }

                    if(node.left == null && node.right == null){
                        return 1;
                    }

                    int left = this.apply(node.left);
                    int right = this.apply(node.right);
                    return left + right;
                }
            };

            return helper.apply(this);
        }

    }

    public static void main(String[] args) throws JsonProcessingException {
        Node<Integer> root = new Node<>(30);
        boolean insert = root.insert(20);
        root.insert(40);
        root.insert(10);
        root.insert(25);
        root.insert(35);
        root.insert(50);

        System.out.println(root);
        System.out.println(root.findLeafNodes());

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(root);
        System.out.println(s);
//
//        Node<String> root2 = new Node<>("m");
//        root2.insert("c");
//        root2.insert("x");
//        root2.insert("a");
//        root2.insert("e");
//        root2.insert("u");
//        root2.insert("z");
//
//        String s1 = objectMapper.writeValueAsString(root2);
//        System.out.println(s1);
    }
}
