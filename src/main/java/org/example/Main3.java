package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

interface ifc{
    default String display(){
//        System.out.println("display");
        return "1212";
    }
}

class C1 implements ifc{

    @Override
    public String toString() {
        return this.display();
    }
}

public class Main3 {
    public static void main(String[] args) {

        C1 c1 = new C1();
        System.out.println(c1.toString());

        String s = "aabbbccdd";

        List<Character> collect =
                s.chars().mapToObj(ch -> (char) Character.toUpperCase(ch)).collect(Collectors.toList());
        System.out.println(collect);

        Map<String, Long> collect1 =
                s.chars().mapToObj(e -> String.valueOf((char) e))
                        .collect(
                                Collectors.groupingBy(e -> e, Collectors.counting())
                        );

        System.out.println(collect1);

        IntSummaryStatistics intSummaryStatistics = s.chars().map(e -> e + 10).summaryStatistics();
        System.out.println(intSummaryStatistics);

//        List<Integer> collect = s.chars().map(e -> e + 1).;
//        System.out.println(collect);


        System.out.println("");
    }
}
