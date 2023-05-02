package Model;


public enum Pawn {

	BLACKState,
	WHITEState,
	NONEState;

	public State getPlayerState() {
		if (this == BLACKState) {
			return State.BLACKState;
		} else if (this == WHITEState) {
			return State.WHITEState;
		} else {
			return State.NONEState;
		}
	}
}

