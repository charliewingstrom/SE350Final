package shop.ui;

// class which replaces UIForm and UIMenu
public class UIthing<T> {
	private final String _heading;
	private final Pair<T>[] _thing;
	
	UIthing(String theHeading, Pair<T>[] theThing) {
		_heading = theHeading;
		_thing = theThing;
	}
	public int size() {
		return _thing.length;
	}
	public String getHeading() {
		return _heading;
	}
	public String getPrompt(int i) {
	    return _thing[i].prompt;
	  }
	
	public void runAction(int i) {
	    ((UIMenuAction) _thing[i].testOrAction).run();
	  }
	public boolean checkInput(int i, String input) {
	    if (null == _thing[i])
	      return true;
	    return ((UIFormTest) _thing[i].testOrAction).run(input);
	}
}