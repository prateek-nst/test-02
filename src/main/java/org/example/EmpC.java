package org.example;

public class EmpC {

    static class Employee implements Comparable<Employee>{
        private String name;

        public Employee(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Employee o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        Employee e1 = new Employee("aa");
        Employee e2 = new Employee("bb");

        System.out.println(e1.compareTo(e2));
        System.out.println(e2.compareTo(e1));
        System.out.println(e1.compareTo(e1));
    }
}
