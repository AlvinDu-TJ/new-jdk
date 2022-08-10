package feature.jdk11.var;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class VarTest {
    public static void main(String[] args) {
        Arrays.asList("Java", "Python", "Ruby")
                .forEach((s) -> {
                    System.out.println("Hello, " + s);
                });

        IntStream.of(1,2,5).forEach(a->{
            System.out.println(a);
        });


        var c  = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        };
        Integer apply = c.apply("234");
        System.out.println("apply = " + apply);

        var b = new BiConsumer<String,String>(){

            @Override
            public void accept(String s, String s2) {
                System.out.println("s+s2 = " + s + s2);
            }
        };

        b.accept("a","b");


        Consumer<String> cd = ( var t)->String.valueOf(t);
    }
}
