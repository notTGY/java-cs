package class_1;
public class Main {
  public static void main(String[] args) {
    Item a = new Item(5);
    Item b = new Item(10);
    Box box = new Box();
    box.add(a);
    box.add(b);

    Item c = new Item(20);
    Box box2 = new Box();
    box2.add(c);

    box.add(box2);
    //box2.add(box); should throw

    System.out.println(box.getW());
  }
}
