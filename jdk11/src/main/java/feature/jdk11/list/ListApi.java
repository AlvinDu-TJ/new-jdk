package feature.jdk11.list;

import java.util.ArrayList;
import java.util.List;

public class ListApi {
    public static void main(String[] args) {
        List<String> list = List.of("Java", "Python", "Ruby");
        // 旧的方法:传入String[]:
        String[] oldway = list.toArray(new String[list.size()]);

        // 新的方法:传入IntFunction:
        String[] newway = list.toArray(String[]::new);


        var s = "world";
        var list1 = new ArrayList<String>();
        list1.add(s);
        list1.add("java");
        list1.add("python");
        list1.stream().map(e -> "Hello, " + e)
                .forEach(System.out::println);

        var list2 = List.of("Java", "Python", "C");
        // 使用of和copyOf创建的集合为不可变集合，不能进行添加、删除、替换、排序等操作，不然会报 java.lang.UnsupportedOperationException 异常。
        var copy = List.copyOf(list2);
        System.out.println(list2 == copy); // true
    }
}
