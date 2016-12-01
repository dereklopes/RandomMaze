
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
