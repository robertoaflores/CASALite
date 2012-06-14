package edu.cnu.casaLite.message;

import java.util.Vector;

public abstract class AbstractParser {
	public  static final String NO_CLOSURE = "Message continues after closing.";

	public final IMessage fromString(String string) {
		IMessage message = getMessage();
		return fromString( message, string );
	}
	
	// must be replaced in subclasses to return an appropriate message type
	public    abstract IMessage getMessage();
	
	protected abstract void    writeOpen (IMessage message, StringBuffer buffer);
	protected abstract void    writeBody (IMessage message, StringBuffer buffer);
	protected abstract void    writeClose(IMessage message, StringBuffer buffer);
		
	protected final String toString(IMessage message) {
		StringBuffer buffer = new StringBuffer();

		writeOpen ( message, buffer );
		writeBody ( message, buffer );
		writeClose( message, buffer );
		
		return buffer.toString();
	}
	
	protected          int readOpen (IMessage message, String[] tokens, int index) { return index; }
	protected abstract int readBody (IMessage message, String[] tokens, int index);
	protected          int readClose(IMessage message, String[] tokens, int index) { return index; }
	
	protected final IMessage fromString(IMessage message, String string) {
		String[] tokens = getTokens( string );
		int      index  = 0;

		index = readOpen ( message, tokens, index );
		index = readBody ( message, tokens, index );
		index = readClose( message, tokens, index );
		// is there more after closing ?
		if (index < tokens.length) {
			throw new IllegalArgumentException( NO_CLOSURE );
		}
		return message;
	}
	
	protected static final char BLANK = ' ';
	private   static final char QUOTE = '"';
	private   static final char SLASH = '\\';
	
	public static String[] getTokens(String string) {
		string = string.trim();

		Vector  buffer = new Vector();
		char[]  array  = string.toCharArray();
		boolean eol    = false;
		int     start  = 0;
		int     end    = 0;

		while (!eol) {
			// get next non-BLANK [or EOL]
			boolean blank = true;
			while ( blank && !eol) {
				if (end == array.length) {
					eol = true;
				}
				else {
					if (array[ start ] == BLANK) start++;
					else                         blank = false;
				}
			}
			// get the next token
			if (!eol) {
				end = start;
				
				boolean slash  = false;
				boolean quote  = false;
				boolean token  = false;
				while (!token) {
					if (end == array.length) {
						token = true;
					}
					else {
						char     c = array[ end ];
						switch ( c ) {
						case QUOTE:
							// if we are not in a quote already, and we have parsed other chars
							// then we end this token and start next token with an opening quote.
							if (!quote && start < end) {
								token = true;
								continue;
							}
							else {
								// "not a slash" means that the previous char was not a slash, which
								// indicates that we have a first-level (opening or closing) quote.
								if (!slash) {
									quote = !quote;
									token = !quote;
								}
							}
							break;

						case BLANK:
							token = !quote;
							// do not include BLANK if not in a quote.
							if (token) {
								continue;
							}
						}
						slash = c == SLASH;
						end++;
					}
				}
				if (start < end) {
					String add = string.substring( start, end ); 
				    buffer.addElement( add );
				    start = end;
				}
			}
		}
		// move to string[]
		String[] tokens = new String[ buffer.size() ];
		for (int i = 0; i < tokens.length; i++) {
			tokens[ i ] = (String) buffer.elementAt( i );
		}
		return tokens;
	}	
}
