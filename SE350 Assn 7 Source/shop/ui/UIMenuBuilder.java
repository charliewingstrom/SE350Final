package shop.ui;

import java.util.ArrayList;
import java.util.List;

public class UIMenuBuilder {
  private final List _menu;
  public UIMenuBuilder() {
    _menu = new ArrayList();
  }
  public UIthing<UIMenuAction> toUIMenu(String heading) {
    if (null == heading)
      throw new IllegalArgumentException();
    if (_menu.size() <= 1)
      throw new IllegalStateException();
    Pair[] array = new Pair[_menu.size()];
    for (int i = 0; i < _menu.size(); i++)
      array[i] = (Pair) (_menu.get(i));
    return new UIthing<UIMenuAction>(heading, array);
  }
  public void add(String prompt, UIMenuAction action) {
    if (null == action)
      throw new IllegalArgumentException();
    _menu.add(new Pair(prompt, action));
  }
}
