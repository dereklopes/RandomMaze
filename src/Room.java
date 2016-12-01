
/**
 * @author Derek Lopes
 * Source credit to "Data Structures and Algorithms in Java" by Robert Lafore
 * 2 Ed. Chapter 13
 */
class Room {
	char label; // name of room
	boolean wasVisited; // whether this room was visited or not
	
	Room(char label) {
		this.label = label;
		wasVisited = false;
	}

	// Accessors
	char getLabel() {
		return label;
	}

	void setLabel(char label) {
		this.label = label;
	}

	// Mutators
	boolean isWasVisited() {
		return wasVisited;
	}

	void setWasVisited(boolean wasVisited) {
		this.wasVisited = wasVisited;
	}
}
