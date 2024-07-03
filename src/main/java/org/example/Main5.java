package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
class Node{
    int key;
    Node left;
    Node right;

    public Node(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Node node = (Node) object;
        return key == node.key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    public static boolean insert(Node root, int key){
        Node newNode = new Node(key);

        Node curr = root;
        Node prev = null;

        while (curr != null){
            if(curr.getKey() == key){
                return false;
            }

            if(key > curr.getKey()){
                prev = curr;
                curr = curr.right;
            } else {
                prev = curr;
                curr = curr.left;
            }
        }

        if(prev == null){
            root = newNode;
            return true;
        }

        if(key > prev.getKey()){
            prev.right = newNode;
        } else {
            prev.left = newNode;
        }

        return true;
    }

    public static ArrayList<Integer> pr(Node root){
        ArrayList<Integer> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if(node != null){
                    result.add(node.getKey());
                    this.accept(node.left);
                    this.accept(node.right);
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public static ArrayList<Integer> ino(Node root){
        ArrayList<Integer> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if(node != null){
                    this.accept(node.left);
                    result.add(node.getKey());
                    this.accept(node.right);
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public static ArrayList<Integer> po(Node root){
        ArrayList<Integer> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if(node != null){
                    this.accept(node.left);
                    this.accept(node.right);
                    result.add(node.getKey());
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public static ArrayList<Integer> bfs(Node root){
        ArrayList<Integer> result = new ArrayList<>();
        HashSet<Integer> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()){
            Node remove = queue.remove();

            result.add(remove.getKey());

            if(remove.left != null){
                queue.add(remove.left);
            }

            if(remove.right != null){
                queue.add(remove.right);
            }
        }

        return result;

    }

    public static Integer findMax(Node root){
        if(root == null){
            return 0;
        }

        Node curr = root;
        Node prev = null;

        while(curr != null){
            prev = curr;
            curr = curr.right;
        }

        return prev.getKey();
    }

    public static Integer findMin(Node root){
        if(root == null){
            return 0;
        }

        Node curr = root;
        Node prev = null;

        while(curr != null){
            prev = curr;
            curr = curr.left;
        }

        return prev.getKey();
    }

    public static Node findNode(Node node, int target){
        if(node == null){
            return null;
        }

        Node curr = node;

        while(curr != null){
            if(curr.getKey() == target){
                return curr;
            }

            if(target > curr.getKey()){
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        return null;
    }

    public static Integer findSucc(Node root, int target){
        if(root == null){
            return null;
        }

        Node foundNode = Node.findNode(root, target);

        if(foundNode == null){
            return null;
        }

        // if foundNode has right child, then the find the min val
        // in that subtree
        if(foundNode.right != null){
            return Node.findMin(foundNode.right);
        }

        Node curr = root;
        Node ancestor = null;

        while(!curr.equals(foundNode)){
            if(curr.getKey() > foundNode.getKey()){
                ancestor = curr;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        return ancestor.getKey();
    }

    public static ArrayList<Integer> sumBranch(Node root){
        if(root == null){
            return null;
        }

        ArrayList<Integer> result = new ArrayList<>();

        BiConsumer<Node, Integer> helper = new BiConsumer<>() {
            @Override
            public void accept(Node node, Integer sum) {
                if(node == null){
                    return;
                }

                if (node.left == null && node.right == null) {
                    result.add(sum + node.getKey());
                    return;
                }

                this.accept(node.left, node.getKey() + sum);
                this.accept(node.right, node.getKey() + sum);
            }
        };

        helper.accept(root, 0);

        return result;
    }
}


public class Main5 {
    public static void main(String[] args) throws JsonProcessingException {
        Node node = new Node(4);
        Node.insert(node, 2);
        Node.insert(node, 6);
        Node.insert(node, 1);
        Node.insert(node, 3);
        Node.insert(node, 5);
        Node.insert(node, 7);
        Node.insert(node, -10);

        System.out.println(node);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(node);
        System.out.println(s);

        System.out.println(Node.pr(node));
        System.out.println(Node.ino(node));
        System.out.println(Node.po(node));

        System.out.println(Node.bfs(node));

        System.out.println(Node.findMax(node));
        System.out.println(Node.findMin(node));

        System.out.println(Node.findNode(node, 1));

        System.out.println(Node.findSucc(node, 4));
        System.out.println(Node.findSucc(node, 3));
        System.out.println(Node.findSucc(node, 5));

        System.out.println(Node.sumBranch(node));
    }
}
