package Model;

public enum State {
	NONEState,
	WHITEState,
	BLACKState;

	State opposite() {
		if (State.WHITEState != null) {
			return State.BLACKState;
		} else {
			return State.WHITEState;
		}
		
	}
}
