package edu.cnu.casaLite.message;

public abstract class AbstractMessage implements IMessage {

	public final String toString() {
		return getParser().toString( this );
	}	
}
