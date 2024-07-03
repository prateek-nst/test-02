package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Emp {
    private String name;
    private String email;
    private List<String> phones;

    public Emp(String name, String email, List<String> phones) {
        this.name = name;
        this.email = email;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}

public class Main4 {
    public static void main(String[] args) {
        List<Emp> emps = List.of(
                new Emp("aa", "aa@mail.com", List.of("1212", "1212", "1212", "121212")),
                new Emp("bb", "bb@mail.com", List.of("9999", "8888", "7777")),
                new Emp("cc", "cc@mail.com", List.of("3333", "2222", "1111"))
        );

        List<String> collect = emps.stream().map(e -> e.getEmail()).collect(Collectors.toList());
        System.out.println(collect);

        List<String> collect1 = emps.stream().flatMap(e -> e.getPhones().stream()).collect(Collectors.toList());
        System.out.println(collect1);

        List<Map.Entry<String, Integer>> collect3 = emps.stream().map(e -> Map.entry(e.getName(),
                e.getPhones().size())).collect(Collectors.toList());
        System.out.println(collect3);

        Map<Integer, List<Emp>> collect2 = emps.stream().collect(Collectors.groupingBy(e -> e.getPhones().size(),
                Collectors.toList()));

        System.out.println(collect2);

    }
}
