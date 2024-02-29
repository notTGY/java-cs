package class_2;
import java.util.Scanner;


public class Main {
  public static void main(String[] args) {
    Secret s = new Secret();
    String result = null;
    Scanner sc = new Scanner(System.in);
    while (result != "Correct") {
      int y = sc.nextInt();
      result = s.guess(y);
      System.out.println(result);
    }
    System.out.println("took " + s.tries + " tries");
    sc.close();
  }
}