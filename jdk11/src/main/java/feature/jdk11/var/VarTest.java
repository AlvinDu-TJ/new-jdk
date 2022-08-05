package feature.jdk11.var;

import java.util.Arrays;

public class VarTest {
    public static void main(String[] args) {
        Arrays.asList("Java", "Python", "Ruby")
                .forEach((s) -> {
                    System.out.println("Hello, " + s);
                });
    }
}
