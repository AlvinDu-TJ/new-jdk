package feature.jdk11.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileApi {

    public static void main(String[] args) throws IOException {
        Files.writeString(
                Path.of("./", "tmp.txt"), // 路径
                "hello, jdk11 files api", // 内容
                StandardCharsets.UTF_8); // 编码
        String s = Files.readString(
                Paths.get("./tmp.txt"), // 路径
                StandardCharsets.UTF_8); // 编码
    }
}
