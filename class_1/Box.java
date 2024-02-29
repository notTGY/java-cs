package class_1;
import java.util.*;

public class Box extends Item {
  private ArrayList<Item> items = new ArrayList<Item>();
  public Box() {
    super(0);
  }
  public void add(Item item) {
    if (item instanceof Box && ((Box)item).has(this)) {
      throw new Error("Cyclic dependencies are forbidden");
    }
    items.add(item);
  }
  public boolean has(Box box) {
    return items.contains(box);
  }
  public int getW() {
    int sum = 0;
    for (int i = 0; i < items.size(); i++) {
      sum += items.get(i).getW();
    }
    return sum;
  }
}
