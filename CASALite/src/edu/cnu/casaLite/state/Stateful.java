package edu.cnu.casaLite.state;

public abstract class Stateful {
	private State state;
	
	protected void setState(State aState) {
		state = aState;
		state.run();
	}
	protected State getState() {
		return state;
	}
}
