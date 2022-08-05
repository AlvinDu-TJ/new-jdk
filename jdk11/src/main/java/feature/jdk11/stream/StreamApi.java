package feature.jdk11.stream;

import java.util.List;

public class StreamApi {

    public static void main(String[] args) {
        var s = "world";
        var list = List.of(s, "java", "python", "go");
        list.stream().map(e -> "Hello, " + e)
                .forEach(System.out::println);
        /**
         * lambda表达式体为 true 打印，遇到 false则不再继续
         */
        list.stream().takeWhile(e -> !startWith(e, "p"))
                .forEach(System.out::println);

        System.out.println("--------------------------------");
        /**
         * lambda表达式体为true不打印，一直到遇到false开始打印
         */
        list.stream().dropWhile(e -> !startWith(e, "p"))
                .forEach(System.out::println);
    }

    private static boolean startWith(String e, String p) {
        return e.startsWith(p);
    }
}
