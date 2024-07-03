package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Consumer;

//                     A
//                 /   |  \
//               B     C   D
//               \    /\   /
//                 e     f
//                  \  /
//                   g

@Data
@NoArgsConstructor
public class G<V> {

    @Data
    public static class Vertex<V> {
        private V value;

        private Set<Vertex<V>> adjacents = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        public boolean removeAdjacent(Vertex<V> vertex) {
            return this.adjacents.remove(vertex);
        }

        public boolean addAdjacent(Vertex<V> vertex) {
            return this.adjacents.add(vertex);
        }

        public boolean isAdjacent(Vertex<V> vertex) {
            return this.adjacents.contains(vertex);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) object;
            return Objects.equals(value, vertex.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString(){
            ArrayList<String> result = new ArrayList<>();

            for (Vertex<V> adjacent : this.getAdjacents()) {
                result.add(adjacent.getValue().toString());
            }

            return  this.value + " -> " + "[" + String.join(",", result) + "]";
        }
    }

    private Map<V, Vertex<V>> vertices = new HashMap<>();
    private boolean directed;

    public void addEdge(V v1, V v2) {
        Vertex<V> vertex1, vertex2;

        vertex1 = this.vertices.containsKey(v1) ? this.vertices.get(v1) : this.addVertex(v1);
        vertex2 = this.vertices.containsKey(v2) ? this.vertices.get(v2) : this.addVertex(v2);

        vertex1.addAdjacent(vertex2);

        if(!directed){
            vertex2.addAdjacent(vertex1);
        }
    }

    public Vertex<V> addVertex(V v) {
        Vertex<V> vertex = new Vertex<>(v);
        this.vertices.put(v, vertex);
        return vertex;
    }

    public Vertex<V> removeVertex(V v) {
        return this.vertices.remove(v);
    }

    public ArrayList<V> bfs(V start){
        Vertex<V> startVertex = this.getVertices().getOrDefault(start, null);

        if(startVertex == null){
            throw new IllegalArgumentException("Non existent Vertex: " + start);
        }

        ArrayList<V> result = new ArrayList<>();
        LinkedList<Vertex<V>> queue = new LinkedList<>();
        HashSet<Vertex<V>> visited = new HashSet<>();

        queue.add(startVertex);
        visited.add(startVertex);

        while (!queue.isEmpty()){
            Vertex<V> remove = queue.remove();
            result.add(remove.getValue());

            for (Vertex<V> adjacent : remove.getAdjacents()) {
                if(!visited.contains(adjacent)){
                    queue.add(adjacent);
                    visited.add(adjacent);
                }
            }
        }

        return result;
    }

    public ArrayList<V> dfs(V start){
        Vertex<V> startVertex = this.getVertices().getOrDefault(start, null);

        if(startVertex == null){
            throw new IllegalArgumentException("Non existent Vertex: " + start);
        }

        ArrayList<V> result = new ArrayList<>();
        HashSet<Vertex<V>> visited = new HashSet<>();

        Consumer<Vertex<V>> helper = new Consumer<Vertex<V>>() {
            @Override
            public void accept(Vertex<V> vVertex) {
                result.add(vVertex.getValue());
                visited.add(vVertex);

                for (Vertex<V> adjacent : vVertex.getAdjacents()) {
                    if(!visited.contains(adjacent)){
                        this.accept(adjacent);
                    }
                }
            }
        };

        helper.accept(startVertex);

        return result;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();

        for (Map.Entry<V, Vertex<V>> entry : vertices.entrySet()) {
            result.append(entry.getValue());
            result.append("\n");
        }

        return result.toString();
    }

    public static void main(String[] args) throws JsonProcessingException {
        G<String> g = new G<>();

        g.addEdge("a", "b");
        g.addEdge("a", "c");
        g.addEdge("a", "d");

        g.addEdge("b", "e");
        g.addEdge("b", "a");

        g.addEdge("c", "a");
        g.addEdge("c", "e");
        g.addEdge("c", "f");

        g.addEdge("d", "a");
        g.addEdge("d", "f");

        g.addEdge("e", "b");
        g.addEdge("e", "c");
        g.addEdge("e", "g");

        g.addEdge("f", "c");
        g.addEdge("f", "d");
        g.addEdge("f", "g");

        g.addEdge("g", "e");
        g.addEdge("g", "f");

        System.out.println(g);

        System.out.println(g.bfs("g"));
        System.out.println(g.dfs("a"));
        System.out.println(g.dfs("g"));

//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(g);
//        System.out.println(s);
    }


}
