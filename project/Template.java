import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Template {
  private String templ;
  public Template() {
    String filePath = "./index.js";
    try {
      templ = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String apply(String tileStr, String mapStr) {
    return templ.replace(
      "//---------- above - preprocessing", 
      "RawSpriteData=" + tileStr + '\n' + "loc2=" + mapStr);
  }
}
