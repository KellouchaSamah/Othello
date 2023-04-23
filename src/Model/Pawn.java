package Model;

/**
 * La classe permet de modeliser les pions du jeu
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
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

