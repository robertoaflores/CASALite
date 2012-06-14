package edu.cnu.casaLite.message;

public final class KQMLParser extends MapParser {
	public  static final String NO_PERFORMATIVE = "Performative missing";
	private static final String PERFORMATIVE    = "performative";

	protected KQMLParser() {
	}
	private static KQMLParser me;
	public  static AbstractParser getInstance() {
		if (me == null) {
			me = new KQMLParser();
		}
		return me;
	}

	// must be replaced in subclasses with appropriate message type
	public IMessage getMessage() {
		return new KQMLMessage();
	}

	protected void writeBody(IMessage message, StringBuffer buffer) {
		KQMLMessage msg          = (KQMLMessage) message;
		String      performative = msg.get( PERFORMATIVE );
		if (performative == null) {
			throw new IllegalArgumentException( NO_PERFORMATIVE );
		}
		buffer.append( performative );
		
		writeBodyExcludingKey( message, buffer, PERFORMATIVE );
	}

	protected int readBody(IMessage message, String[] tokens, int index) {
		KQMLMessage msg         = (KQMLMessage) message;
		// getPerformative
		String     performative = getPerformative( tokens, index );
		if (performative.equals( CLOSE )) { 
			return index;
		}
		else {
			index++;
			msg.set( PERFORMATIVE, performative );
			return super.readBody( message, tokens, index );
		}
	}

	private String getPerformative(String[] tokens, int index) {
		if (index < tokens.length) {
			if (tokens[index].startsWith( COLON )) {
				throw new IllegalArgumentException( NO_PERFORMATIVE );
			}
			if (tokens[index].equals( CLOSE )) {
				return CLOSE;
			}
			return tokens[ index ];
		}
		throw new IllegalArgumentException( NO_PERFORMATIVE );
	}
}
