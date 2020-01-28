package shop.data;


/**
 * Implementation of Video interface.
 * @see Data
 */
final class VideoObj implements Video {
  private final String _title;
  private final int    _year;
  private final String _director;

  /**
   * Initialize all object attributes.
   */
  VideoObj(String title, int year, String director) {
	if (title==null || title.trim().equals("")) {throw new IllegalArgumentException("Title must not be null or the empty string");}
	else this._title = title.trim();
	if (year<=1800 || year>=5000) {throw new IllegalArgumentException("Year must be between 1800 and 5000");}
	else {this._year=year;}
	if (director==null || director.trim().equals("")) {throw new IllegalArgumentException("Director must not be null or the empty string");}
	else this._director=director.trim();
  }

  public String director() {
    // TODO  
	return this._director;
  }

  public String title() {
    // TODO  
	return this._title;
  }

  public int year() {
    // TODO  
	return this._year;
  }

  public boolean equals(Object thatObject) {
    // TODO  
	if (this == thatObject) return true;
	
	if (thatObject == null || thatObject.getClass()!=this.getClass()) return false;
	VideoObj v = (VideoObj) thatObject;
	return (this.director().equals(v.director())&&
			this.title().equals(v.title())&&
			this.year()==v.year());
			
  }

  public int hashCode() {
    // TODO  
	int result = 17;
	result = 37*result + this.title().hashCode();
	result = 37*result + this.year();
	result = 37*result + this.director().hashCode();
		
	return result;
  }

  public int compareTo(Object thatObject) {
    // TODO  
	if (thatObject.getClass()!=this.getClass()) throw new ClassCastException("thatObject has an incompatible type");
		else {
			int ans = this.title().compareTo(((VideoObj)thatObject).title());
			if (ans!=0) return ans;
			ans = this.year()-((VideoObj)thatObject).year();
			if (ans!=0) return ans;
			ans = this.director().compareTo(((VideoObj)thatObject).director());
			return ans;
		}
  }

  public String toString() {
    // TODO  
	String year = Integer.toString(this.year());
	String ans = this.title() + " (" + year + ") : " + this.director();
		
	return ans;
  }
}
