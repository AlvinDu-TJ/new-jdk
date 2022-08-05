package feature.jdk11;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Consumer<String> consumer = t -> System.out.println(t.toUpperCase());
    }
}
