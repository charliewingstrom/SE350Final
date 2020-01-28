package shop.ui;
public class Pair<T> {
	final String prompt;
	final T testOrAction;
	
	Pair(String thePrompt, T theTestOrAction) {
		prompt = thePrompt;
		testOrAction = theTestOrAction;
	}
}