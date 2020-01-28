package shop.ui;

/**
 * @see UIMenuBuilder
 */
//UIthing<UIMenuAction> 
public final class UIMenu extends UIthing<UIMenuAction>{
  String _heading;
  Pair<UIMenuAction>[] _menu;

  UIMenu(String heading, Pair<UIMenuAction>[] menu) {
    _heading = heading;
    _menu = menu;
  }

  public int size() {
    return _menu.length;
  }
  /*
  public String getHeading() {
    return _heading;
  }*/
  public String getPrompt(int i) {
    return _menu[i].prompt;
  }
  
  public void runAction(int i) {
	(_menu[i].testOrAction).run();
  }
}


