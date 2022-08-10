package feature.jdk11.guava;

import com.google.common.collect.Iterables;

import java.util.List;

public class GuavaMain {

    public static void main(String[] args) {
        List<String> a = List.of("a","b","c");
        List<String> b = List.of("a","b","c");
        // guava 的api 必须顺序一样才可以
        boolean b1 = Iterables.elementsEqual(a, b);
        System.out.println("b1 = " + b1);
    }
}
