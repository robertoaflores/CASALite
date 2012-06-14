package edu.cnu.casaLite.message;

public class KQMLMessage extends MapMessage {
	private static final AbstractParser parser = KQMLParser.getInstance();
	
	// must be replaced in subclasses with appropriate parser
	public static MapMessage fromString(String aString) {
		return (KQMLMessage) parser.fromString( aString );
	}
	// must be replaced in subclasses with appropriate parser
	public AbstractParser getParser() {
		return parser;
	}
}
