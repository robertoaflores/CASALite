package edu.cnu.casaLite.message;

import java.util.Enumeration;

public class MessageParser extends AbstractParser {
	public    static final String NO_OPEN  = "Opening parenthesis missing.";
	public    static final String NO_CLOSE = "Closing parenthesis missing.";

	protected static final String OPEN     = "(";
	protected static final String CLOSE    = ")";

	protected MessageParser() {
	}
	private static MessageParser me;
	public  static AbstractParser getInstance() {
		if (me == null) {
			me = new MessageParser();
		}
		return me;
	}

	// must be replaced in subclasses with appropriate message type
	public IMessage getMessage() {
		return new Message();
	}

	protected int readOpen(IMessage message, String[] tokens, int index) {
		if (index < tokens.length) {
			if (tokens[ index++ ].equals( OPEN )) {
				return index;
			}
		}
		throw new IllegalArgumentException( NO_OPEN );
	}
	
	protected int readClose(IMessage message, String[] tokens, int index) {
		if (index < tokens.length) {
			if (tokens[ index++ ].equals( CLOSE )) {
				return index;
			}
		}
		throw new IllegalArgumentException( NO_CLOSE );
	}

	protected int readBody(IMessage message, String[] tokens, int index) {
		Message msg = (Message) message;
		while (index < tokens.length) {
			if (tokens[ index ].equals( CLOSE )) {
				break;
			}
			msg.add( tokens[ index++ ] );
		}
		return index;
	}

	protected void writeOpen(IMessage message, StringBuffer buffer) {
		buffer.append( OPEN + BLANK );
	}

	protected void writeBody(IMessage message, StringBuffer buffer) {
		Message     msg = (Message) message;
		Enumeration i   = msg.getValues();
		while (i.hasMoreElements()) {
			String value = (String) i.nextElement();
			buffer.append( BLANK + value );
		}
	}
	
	protected void writeClose(IMessage message, StringBuffer buffer) {
		buffer.append( BLANK + CLOSE );
	}
}
