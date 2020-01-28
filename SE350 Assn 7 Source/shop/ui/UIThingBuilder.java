package shop.ui;

import java.util.ArrayList;
import java.util.List;
// this class replaces UIMenuBuilder/UIFormBuilder by using a generic type
public class UIThingBuilder<T> {
	private List _menu;
	public UIThingBuilder() {
		_menu = new ArrayList();
	}
	
	public UIthing<T> toUIthing(String heading) {
		if (null == heading)
			throw new IllegalArgumentException();
		if (_menu.size() < 1)
			throw new IllegalStateException();
		Pair[] array = new Pair[_menu.size()];
		for (int i=0; i<_menu.size(); i++)
			array[i] = (Pair) (_menu.get(i));
		return new UIthing<T>(heading, array);
	}
	public void add(String prompt, T thing) {
		_menu.add(new Pair(prompt, thing));
	}
}