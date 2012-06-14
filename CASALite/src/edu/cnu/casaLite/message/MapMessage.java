package edu.cnu.casaLite.message;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapMessage extends AbstractMessage {
	private static final AbstractParser parser = MapParser.getInstance();
	private Hashtable map;

	public MapMessage() {
		map = new Hashtable();
	}
	public AbstractParser getParser() {
		return parser;
	}
	public static MapMessage fromString(String aString) {
		return (MapMessage) parser.fromString( aString );
	}
	
	public void set(String key, String value) {
		map.put( key, new Quote( value ));
	}

	public String get(String key) {
		Quote   value = (Quote) map.get( key );
		return (value != null) ? value.get(): null;
	}	
	public String getQuoted(String key, boolean quoted) {
		Quote   value = (Quote) map.get( key );
		return (value != null) ? value.get( quoted ): null;
	}
	
	public Enumeration getKeys() {
		return map.keys();
	}
	public int getSize() {
		return map.size();
	}
}
