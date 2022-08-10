package feature.jdk11.commons;


import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class CollectionUtilsMain {

    public static void main(String[] args) {
        List<String> a = List.of("a","b","c");
        List<String> b = List.of("c","a","b");
        boolean equalCollection = CollectionUtils.isEqualCollection(a, b);
        System.out.println("equalCollection = " + equalCollection);
    }
}
