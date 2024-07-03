package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

interface TriConsumer<A, B, C>{
    void accept(A a, B b, C c);
}

@Data
class TNode<K, V>{
    private K key;
    private V value;

    @ToString.Exclude
    private ArrayList<TNode<K, V>> children = new ArrayList<>();

    public TNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void add(TNode<K, V> tNode){
        this.children.add(tNode);
    }

    public ArrayList<TNode<K, V>> bfs(){
        ArrayList<TNode<K, V>> result = new ArrayList<>();
        LinkedList<TNode<K, V>> queue = new LinkedList<>();

        queue.add(this);

        while (!queue.isEmpty()){
            TNode<K, V> remove = queue.remove();
            result.add(remove);

            for (TNode<K, V> child : remove.getChildren()) {
                queue.add(child);
            }
        }

        return result;
    }

    public ArrayList<TNode<K, V>> dfs(){
        ArrayList<TNode<K, V>> result = new ArrayList<>();

        Consumer<TNode<K, V>> helper = new Consumer<TNode<K, V>>() {
            @Override
            public void accept(TNode<K, V> node) {
                if(node == null){
                    return;
                }

                result.add(node);

                for (TNode<K, V> child : node.getChildren()) {
                    this.accept(child);
                }
            }
        };

        helper.accept(this);

        return result;
    }

    public void toc(){
        TriConsumer<TNode<K, V>, Integer, Integer> helper = new TriConsumer<TNode<K, V>, Integer, Integer>() {
            @Override
            public void accept(TNode<K, V> node, Integer depth, Integer counter) {
                if(node == null){
                    return;
                }

                String numbering = "";

                if(node.getChildren().isEmpty()){
                    numbering = depth + ":" + counter + ":" + node.getKey();
                } else {
                    numbering = counter + ":" + node.getKey();
                }

                System.out.println(" ".repeat(depth * 4) + numbering);

                for (int i = 0; i < node.getChildren().size(); i++) {
                    this.accept(node.getChildren().get(i), depth + 1, i + 1);
                }

            }
        };

        helper.accept(this, 1, 1);
    }
}

public class Main6 {
    public static void main(String[] args) throws JsonProcessingException {
        TNode<String, String> electronics = new TNode<>("electronics", "");

        TNode<String, String> mobile = new TNode<>("mobile", "");
        TNode<String, String> laptops = new TNode<>("laptops", "");
        TNode<String, String> accessories = new TNode<>("accessories", "");

        electronics.add(mobile);
        electronics.add(laptops);
        electronics.add(accessories);

        TNode<String, String> apple = new TNode<>("apple", "");
        TNode<String, String> samsung = new TNode<>("samsung", "");

        mobile.add(apple);
        mobile.add(samsung);

        TNode<String, String> dell = new TNode<>("dell", "");
        TNode<String, String> asus = new TNode<>("asus", "");

        laptops.add(dell);
        laptops.add(asus);

        TNode<String, String> headphones = new TNode<>("headphones", "");
        TNode<String, String> chargers = new TNode<>("chargers", "");
        TNode<String, String> cases = new TNode<>("cases", "");

        accessories.add(headphones);
        accessories.add(chargers);
        accessories.add(cases);

        TNode<String, String> iphone4s = new TNode<>("iphone 4s", "");
        TNode<String, String> iphone6s = new TNode<>("iphone 6s", "");

        apple.add(iphone4s);
        apple.add(iphone6s);

        System.out.println(electronics);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(electronics);
        System.out.println(s);

        System.out.println(electronics.bfs());

        System.out.println(electronics.dfs());
        electronics.toc();

    }
}
