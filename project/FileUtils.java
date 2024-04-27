import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static int[] read1DFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            content = content.replace("[", "").replace("]", "");
            String[] strings = content.split(",");
            int[] array = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                array[i] = Integer.parseInt(strings[i].trim());
            }
            return array;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }

    public static void write1DFile(int[] array, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("[");
            for (int i = 0; i < array.length; i++) {
                writer.write(String.valueOf(array[i]));
                if (i < array.length - 1) {
                    writer.write(", ");
                }
            }
            writer.write("]");
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + filePath, e);
        }
    }
}