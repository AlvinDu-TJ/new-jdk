package feature.jdk11.string;

public class StringApi {

    public static void main(String[] args) {
        String s = " Hello, JDK11!\u3000\u3000";
        System.out.println("     original: [" + s + "]");
        System.out.println("         trim: [" + s.trim() + "]");
        System.out.println("        strip: [" + s.strip() + "]");
        System.out.println(" stripLeading: [" + s.stripLeading() + "]");
        System.out.println("stripTrailing: [" + s.stripTrailing() + "]");

        String s1 = " \u3000"; // 由一个空格和一个中文空格构成
        System.out.println(s1.isEmpty()); // false
        System.out.println(s1.isBlank()); // true

        String s2 = "Java\nPython\nRuby";
        s2.lines().forEach(System.out::println);

        System.out.println("-".repeat(10)); // 打印----------
    }
}
