package class_2;
public class Secret {
  private int x = 1;
  public int tries = 0;
  Secret() {
    x = (int)Math.floor(Math.random() * 100) + 1;
  }
  String guess(int y) {
    tries++;
    if (y == x) {
      return "Correct";
    } else if (y < x) {
      return "Too small";
    } else {
      return "Too big";
    }
  }
}
