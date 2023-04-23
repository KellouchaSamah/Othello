package Model;

/**
 * La classe permet de modeliser le etats possible d'une case
 * @author M1 info Rouen (2019/2020)
 * Othello
 */
public enum State {
	// les etats possibles 
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
