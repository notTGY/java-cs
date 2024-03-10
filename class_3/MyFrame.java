package class_3;
import java.awt.*;

public class MyFrame extends Frame {
  String content = "hello ";

  public MyFrame() {
    super("MyFrame");
    setSize(400, 300);
    setVisible(true);

    Label text = new Label(content);
    add(text);

    Button ok = new Button("ok");
    OnClick onClick = new OnClick();
    ok.addActionListener(onClick);

    add(ok);
  }
  paint() {

  }
}
