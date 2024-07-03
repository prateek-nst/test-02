package org.example;

import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Class<String> stringClass = String.class;

        List<Map<String, String>> listMapValue = getValue(new TypeReference<>() {});
        System.out.println("Returned value for List<Map<String, String>>: " + listMapValue);

        List<Integer> value = getValue(new TypeReference<List<Integer>>() {});
        System.out.println(value);

    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(TypeReference<T> typeReference){
        Type type = typeReference.getType();

        if(type.getTypeName().equals("java.util.List<java.util.Map<java.lang.String, java.lang.String>>")){
            return (T) List.of(Map.of("k1", "v1"));
        }

        if(type.getTypeName().equals("java.util.List<java.lang.Integer>")){
            // how to detect compile time error here
//            return (T) List.of(Map.of("k1", "v1"));
            return (T) List.of(1, 2);
        }

        System.out.println(type.getTypeName());

        return null;
    }
}