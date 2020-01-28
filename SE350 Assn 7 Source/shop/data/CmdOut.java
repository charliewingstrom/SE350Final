package shop.data;

import shop.command.UndoableCommand;

/**
 * Implementation of command to check out a video.
 * @see Data
 */
final class CmdOut implements UndoableCommand {
  private InventorySet _inventory;
  private Record _oldvalue;
  private Video _video;
  CmdOut(InventorySet inventory, Video video) {
    _inventory = inventory;
    _video = video;
  }
  public boolean run() {
    // TODO  
	  try {
		  	this._oldvalue = this._inventory.checkOut(_video);
			_inventory.getHistory().add(this);
			return true;
		} catch (ClassCastException | IllegalArgumentException e) {
			return false;
		}
  }
  public void undo() {
    // TODO  
	this._inventory.replaceEntry(this._video, _oldvalue);
  }
  public void redo() {
    // TODO  
	this._inventory.checkOut(_video);
	
  }
}
