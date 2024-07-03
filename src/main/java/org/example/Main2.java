package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {
    public static void main(String[] args) {
        Object arr = call();
        System.out.println(new ArrayList<String>() instanceof Collection);
        System.out.println(new HashMap<>() instanceof Collection);
        List<String> collect = Stream.of((ArrayList<?>) arr).map(e -> String.valueOf(e).toLowerCase(Locale.ROOT)).toList();

        for (String s : collect) {
            System.out.println(s);
        }
    }

    public static Object call(){
        return new ArrayList<>(List.<String>of("one", "two", "three"));
    }

//    public static List<?> convertObjectToList(Object obj) {
//        List<?> list = new ArrayList<>();
//        if (obj.getClass().isArray()) {
//            list = Arrays.asList((Object[])obj);
//        } else if (obj instanceof Collection<?>) {
//            list = new ArrayList<>((Collection<?>)obj);
//        }
//        return list;
//    }


}
