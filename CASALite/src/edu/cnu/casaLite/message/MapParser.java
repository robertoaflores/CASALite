package edu.cnu.casaLite.message;

import java.util.Enumeration;

public class MapParser extends MessageParser {
	public    static final String NO_KEY   = "Parameter key expected.";
	public    static final String NO_VALUE = "Parameter value expected.";

	protected static final String COLON    = ":";

	protected MapParser() {
	}
	private static MapParser me;
	public  static AbstractParser getInstance() {
		if (me == null) {
			me = new MapParser();
		}
		return me;
	}

	// must be replaced in subclasses with appropriate message type
	public IMessage getMessage() {
		return new MapMessage();
	}
	
	protected void writeBody(IMessage message, StringBuffer buffer) {
		writeBodyExcludingKey( message, buffer, "" );
	}
	protected void writeBodyExcludingKey(IMessage message, StringBuffer buffer, String excluded) {
		MapMessage  msg = (MapMessage) message;
		Enumeration i   = msg.getKeys();
		while (i.hasMoreElements()) {
			String key = (String) i.nextElement();
			if (!key.equals(excluded)) {
				String value = msg.getQuoted( key, true );
				buffer.append( BLANK + COLON + key + 
			                   BLANK         + value );
			}
		}
	}
	
	protected int readBody(IMessage message, String[] tokens, int index) {
		MapMessage msg = (MapMessage) message;
		while (true) {
			// getKey
			String key = getKey( tokens, index );
			if (key.equals( CLOSE )) {
				break;
			}
			else {
				// getValue
				index++;
				String value = getValue( tokens, index );
				index++;
				// add pair
				msg.set( key.substring( 1 ), value );
			}			
		}
		return index;
	}

	private String getKey(String[] tokens, int index) {
		if (index < tokens.length) {
			if (tokens[index].startsWith( COLON )) {
				return tokens[ index ];
			}
			if (tokens[index].equals( CLOSE )) {
				return CLOSE;
			}
			throw new IllegalArgumentException( NO_KEY );
		}
		throw new IllegalArgumentException( NO_CLOSE );
	}
	
	private String getValue(String[] tokens, int index) {
		if (index < tokens.length) {
			return tokens[ index ];
		}
		throw new IllegalArgumentException( NO_VALUE );
	}
}