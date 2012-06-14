package edu.cnu.casaLite.message;

import java.util.Enumeration;
import java.util.Vector;

public class Message extends AbstractMessage {
	private static final AbstractParser parser = MessageParser.getInstance();
	private Vector vector;
		
	public Message() {
		vector = new Vector();
	}

	public AbstractParser getParser() {
		return parser;
	}
	public static Message fromString(String aString) {
		return (Message) parser.fromString( aString );
	}

	public void add(String value) {
		vector.addElement( value );
	}
	public Enumeration getValues() {
		return vector.elements();
	}
	public int getSize() {
		return vector.size();
	}
}