package shop.data;

import shop.command.RerunnableCommand;
import shop.command.UndoableCommand;
import java.util.HashMap;
/**
 * A static class for accessing data objects.
 */
public class Data {
	// videos hashMap is used for hashConsing video objects
	static HashMap<Integer, Video> videos = new HashMap<Integer, Video>();
  private Data() {}
  /**
   * Returns a new Inventory.
   */
  static public final Inventory newInventory() {
    return new InventorySet();
  }

  /**
   * Factory method for Video objects.
   * Title and director are "trimmed" to remove leading and final space.
   * @throws IllegalArgumentException if Video invariant violated.
   */
  
  // I changed this to fit with the hashConsing pattern. 
  static public Video newVideo(String title, int year, String director) {
    // TODO  
	Video v = new VideoObj(title,year,director);
	if (videos.containsValue(v)) {
		return videos.get(v.hashCode());
	}
	else {
		videos.put(v.hashCode(), v);
		return videos.get(v.hashCode());
	}
  }

  /**
   * Returns a command to add or remove copies of a video from the inventory.
   * <p>The returned command has the following behavior:</p>
   * <ul>
   * <li>If a video record is not already present (and change is
   * positive), a record is created.</li>
   * <li>If a record is already present, <code>numOwned</code> is
   * modified using <code>change</code>.</li>
   * <li>If <code>change</code> brings the number of copies to be less
   * than one, the record is removed from the inventory.</li>
   * </ul>
   * @param video the video to be added.
   * @param change the number of copies to add (or remove if negative).
   * @throws IllegalArgumentException if <code>inventory<code> not created by a call to <code>newInventory</code>.
   */
  static public UndoableCommand newAddCmd(Inventory inventory, Video video, int change) {
    if (!(inventory instanceof InventorySet))
      throw new IllegalArgumentException();
    return new CmdAdd((InventorySet) inventory, video, change);
  }

  /**
   * Returns a command to check out a video.
   * @param video the video to be checked out.
   */
  static public UndoableCommand newOutCmd(Inventory inventory, Video video) {
    // TODO  
	if (!(inventory instanceof InventorySet))
		throw new IllegalArgumentException();
	return new CmdOut((InventorySet) inventory, video);
  }
  
  /**
   * Returns a command to check in a video.
   * @param video the video to be checked in.
   */
  static public UndoableCommand newInCmd(Inventory inventory, Video video) {
    // TODO  
	if (!(inventory instanceof InventorySet))
		throw new IllegalArgumentException();
	return new CmdIn((InventorySet) inventory, video);
  }
  
  /**
   * Returns a command to remove all records from the inventory.
   */
  static public UndoableCommand newClearCmd(Inventory inventory) {
    if (!(inventory instanceof InventorySet))
      throw new IllegalArgumentException();
    return new CmdClear((InventorySet) inventory);
  }

  /**
   * Returns a command to undo that will undo the last successful UndoableCommand. 
   */
  static public RerunnableCommand newUndoCmd(Inventory inventory) {
    // TODO  
	  if (!(inventory instanceof InventorySet))
	      throw new IllegalArgumentException();
	 return ((InventorySet) inventory).getHistory().getUndo();
  }

  /**
   * Returns a command to redo that last successfully undone command. 
   */
  static public RerunnableCommand newRedoCmd(Inventory inventory) {
    // TODO  
	if (!(inventory instanceof InventorySet))
	    throw new IllegalArgumentException();
	return ((InventorySet) inventory).getHistory().getRedo();
  }
}  
