package shop.data;

import shop.command.UndoableCommand;

/**
 * Implementation of command to check in a video.
 * @see Data
 */
final class CmdIn implements UndoableCommand {
  private InventorySet _inventory;
  private Record _oldvalue;
  private Video _video;
  CmdIn(InventorySet inventory, Video video) {
    _inventory = inventory;
    _video = video;
  }
  public boolean run() {
    // TODO  
	  try {
		  	this._oldvalue = this._inventory.checkIn(_video);
			this._inventory.getHistory().add(this);
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
	this._inventory.checkIn(_video);
  }
}
