package shop.main;

import shop.ui.Pair;
import shop.ui.UI;
import shop.ui.UIMenuAction;
import shop.ui.UIthing;
import shop.ui.UIThingBuilder;
import shop.ui.UIError;
import shop.ui.UIFormTest;
import shop.data.Data;
import shop.data.Inventory;
import shop.data.Video;
import shop.data.Record;
import shop.command.Command;
import java.util.Iterator;
import java.util.Comparator;

class Control {
  private static final int EXITED = 0;
  private static final int EXIT = 1;
  private static final int START = 2;
  private static final int NUMSTATES = 10;
  private UIthing<UIMenuAction>[] _menus;
  private static int _state;

  private static UIthing<UIFormTest> _getVideoForm;
  private static Inventory _inventory;
  private static UI _ui;
  
  // new enum to define the tests
  private enum tests {
	  numberTest(new UIFormTest() {
			        public boolean run(String input) {
			            try {
			              Integer.parseInt(input);
			              return true;
			            } catch (NumberFormatException e) {
			              return false;
			            }
			          }
			        }),
	  stringTest(new UIFormTest() {
			        public boolean run(String input) {
			          return ! "".equals(input.trim());
			        }
			      }),
	  yearTest(new UIFormTest() {
			        public boolean run(String input) {
			          try {
			            int i = Integer.parseInt(input);
			            return i > 1800 && i < 5000;
			          } catch (NumberFormatException e) {
			            return false;
			          }
			        }
			      });
	  
	  private UIFormTest test;
	  tests(UIFormTest test) {
		  this.test = test;
	  }

  }
  
  Control(Inventory inventory, UI ui) {
    _inventory = inventory;
    _ui = ui;

    _menus = new UIthing[NUMSTATES];
    _state = START;
    addSTART(START);
    addEXIT(EXIT);

    UIThingBuilder<UIFormTest> f = new UIThingBuilder<UIFormTest>();
    f.add("Title", tests.stringTest.test);
    f.add("Year", tests.yearTest.test);
    f.add("Director", tests.stringTest.test);
    _getVideoForm = f.toUIthing("Enter Video");
  }
  
  void run() {
    try {
      while (_state != EXITED) {
        _ui.processMenu(_menus[_state]);
      }
    } catch (UIError e) {
      _ui.displayError("UI closed");
    }
  }

  // new enum to define the actions
  private enum actions {
	  
	  DefaultStart("Default",
      new UIMenuAction() {
        public void run() {
          _ui.displayError("doh!");
        }
      }),
	  AddOrRemoveCopies("Add/Remove copies of a video",
		      new UIMenuAction() {
	        public void run() {
	          String[] result1 = _ui.processForm(_getVideoForm);
	          Video v = Data.newVideo(result1[0], Integer.parseInt(result1[1]), result1[2]);

	          UIThingBuilder<UIFormTest> f = new UIThingBuilder<UIFormTest>();
	          f.add("Number of copies to add/remove", tests.numberTest.test);
	          String[] result2 = _ui.processForm(f.toUIthing(""));
	                                             
	          Command c = Data.newAddCmd(_inventory, v, Integer.parseInt(result2[0]));
	          if (! c.run()) {
	            _ui.displayError("Command failed");
	          }
	        }
	      }),
	  CheckIn("Check in a video",
      new UIMenuAction() {
        public void run() {
          String[] result = _ui.processForm(_getVideoForm);
          Video v = Data.newVideo(result[0], Integer.parseInt(result[1]), result[2]);
          
          Command c = Data.newInCmd(_inventory, v);
          if (!c.run()) _ui.displayError("Check in Failed");
        }
      }),
	  CheckOut("Check out a video",
      new UIMenuAction() {
        public void run() {
          // TODO  
            String[] result = _ui.processForm(_getVideoForm);
            Video v = Data.newVideo(result[0], Integer.parseInt(result[1]), result[2]);
            
            Command c = Data.newOutCmd(_inventory, v);
            if (!c.run()) _ui.displayError("Check out Failed");
        }
      }),
	  Print("Print the inventory",
      new UIMenuAction() {
        public void run() {
          _ui.displayMessage(_inventory.toString());
        }
      }),
	  Clear("Clear the inventory",
      new UIMenuAction() {
        public void run() {
          if (!Data.newClearCmd(_inventory).run()) {
            _ui.displayError("Command failed");
          }
        }
      }),
	  Undo("Undo",
      new UIMenuAction() {
        public void run() {
          if (!Data.newUndoCmd(_inventory).run()) {
            _ui.displayError("Command failed");
          }
        }
      }),
	  Redo("Redo",
		      new UIMenuAction() {
	        public void run() {
	          if (!Data.newRedoCmd(_inventory).run()) {
	            _ui.displayError("Command failed");
	          }
	        }
	      }),
	  TopTen("Print top ten all time rentals in order",
		      new UIMenuAction() {
	        public void run() {
	          // TODO  
	        Comparator<Record> c = new Comparator<Record>() {
				public int compare(Record r1, Record r2) {
					return r2.numRentals()-r1.numRentals();
					}
	        	};
	        Iterator<Record> i = _inventory.iterator(c);
	        StringBuffer ans = new StringBuffer();
	        int j = 10;
	        while(i.hasNext() && j>0) {
	        	ans.append(i.next());
	        	ans.append("\n");
	        	j--;
	        }
	        _ui.displayMessage(ans.toString());
	        }
	      }),
	  Exit("Exit",
      new UIMenuAction() {
        public void run() {
          _state = EXIT;
        }
      }),
	  Initialize("Initialize with bogus contents",
		      new UIMenuAction() {
	        public void run() {
	          Data.newAddCmd(_inventory, Data.newVideo("a", 2000, "m"), 1).run();
	          Data.newAddCmd(_inventory, Data.newVideo("b", 2000, "m"), 2).run();
	          Data.newAddCmd(_inventory, Data.newVideo("c", 2000, "m"), 3).run();
	          Data.newAddCmd(_inventory, Data.newVideo("d", 2000, "m"), 4).run();
	          Data.newAddCmd(_inventory, Data.newVideo("e", 2000, "m"), 5).run();
	          Data.newAddCmd(_inventory, Data.newVideo("f", 2000, "m"), 6).run();
	          Data.newAddCmd(_inventory, Data.newVideo("g", 2000, "m"), 7).run();
	          Data.newAddCmd(_inventory, Data.newVideo("h", 2000, "m"), 8).run();
	          Data.newAddCmd(_inventory, Data.newVideo("i", 2000, "m"), 9).run();
	          Data.newAddCmd(_inventory, Data.newVideo("j", 2000, "m"), 10).run();
	          Data.newAddCmd(_inventory, Data.newVideo("k", 2000, "m"), 11).run();
	          Data.newAddCmd(_inventory, Data.newVideo("l", 2000, "m"), 12).run();
	          Data.newAddCmd(_inventory, Data.newVideo("m", 2000, "m"), 13).run();
	          Data.newAddCmd(_inventory, Data.newVideo("n", 2000, "m"), 14).run();
	          Data.newAddCmd(_inventory, Data.newVideo("o", 2000, "m"), 15).run();
	          Data.newAddCmd(_inventory, Data.newVideo("p", 2000, "m"), 16).run();
	          Data.newAddCmd(_inventory, Data.newVideo("q", 2000, "m"), 17).run();
	          Data.newAddCmd(_inventory, Data.newVideo("r", 2000, "m"), 18).run();
	          Data.newAddCmd(_inventory, Data.newVideo("s", 2000, "m"), 19).run();
	          Data.newAddCmd(_inventory, Data.newVideo("t", 2000, "m"), 20).run();
	          Data.newAddCmd(_inventory, Data.newVideo("u", 2000, "m"), 21).run();
	          Data.newAddCmd(_inventory, Data.newVideo("v", 2000, "m"), 22).run();
	          Data.newAddCmd(_inventory, Data.newVideo("w", 2000, "m"), 23).run();
	          Data.newAddCmd(_inventory, Data.newVideo("x", 2000, "m"), 24).run();
	          Data.newAddCmd(_inventory, Data.newVideo("y", 2000, "m"), 25).run();
	          Data.newAddCmd(_inventory, Data.newVideo("z", 2000, "m"), 26).run();
	        }
	      }),
	  DefaultExit("Default", new UIMenuAction() { public void run() {} }),
	  Yes("Yes",
      new UIMenuAction() {
        public void run() {
          _state = EXITED;
        }
      }),
	  No("No",
      new UIMenuAction() {
        public void run() {
          _state = START;
        }
      });
	  
	  private final String label;
	  private final UIMenuAction action;
	  private actions(String label, UIMenuAction action) {
		  this.label = label;
		  this.action = action;
	  }
  }
  private void addSTART(int stateNum) {
    UIThingBuilder<UIMenuAction> m = new UIThingBuilder<UIMenuAction>();
    
    m.add(actions.DefaultStart.label, actions.DefaultStart.action);
    m.add(actions.AddOrRemoveCopies.label, actions.AddOrRemoveCopies.action);
    m.add(actions.CheckIn.label, actions.CheckIn.action);
    m.add(actions.CheckOut.label, actions.CheckOut.action);
    m.add(actions.Print.label, actions.Print.action);
    m.add(actions.Clear.label, actions.Clear.action);
    m.add(actions.Undo.label, actions.Undo.action);
    m.add(actions.Redo.label, actions.Redo.action);
    m.add(actions.TopTen.label, actions.TopTen.action);
          
    m.add(actions.Exit.label, actions.Exit.action);
    
    m.add(actions.Initialize.label, actions.Initialize.action);
    
    _menus[stateNum] = m.toUIthing("Charlie's Video");
  }
  private void addEXIT(int stateNum) {
    UIThingBuilder<UIMenuAction> m = new UIThingBuilder<UIMenuAction>();
    
    m.add(actions.DefaultExit.label, actions.DefaultExit.action);
    m.add(actions.Yes.label, actions.Yes.action);
    m.add(actions.No.label, actions.No.action);
    
    _menus[stateNum] = m.toUIthing("Are you sure you want to exit?");
  }
}
