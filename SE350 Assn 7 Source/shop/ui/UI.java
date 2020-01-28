package shop.ui;

public interface UI {
  public void processMenu(UIthing<UIMenuAction> menu);
  public String[] processForm(UIthing<UIFormTest> _getVideoForm);
  public void displayMessage(String message);
  public void displayError(String message);
}
