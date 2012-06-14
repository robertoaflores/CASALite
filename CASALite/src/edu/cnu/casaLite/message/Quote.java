package edu.cnu.casaLite.message;

public class Quote {
	private static final char QUOTE = '"';
    private static final char SLASH = '\\';
    private static final char BLANK = ' ';

	private String given;
	private String quoted;
	private String unquoted;
	
	public Quote(String aString) {
		given = aString;
		
		if (hasQuotes( given )) {
			quoted   = given;
			unquoted = removeQuotes( given );
		}
		else {
			quoted   = needsQuotes(given) ? addQuotes(given) : given;
			unquoted = given;
		}
	}
	public String get() {
		return given;
	}
	public String get(boolean quotes) {
		return quotes ? quoted : unquoted;
	}
	public static String addQuotes(String s) {
		StringBuffer buffer = new StringBuffer( s );

		int    i = buffer.length();
		while (i-- > 0) {
			char     c = buffer.charAt( i );
			switch ( c ) {
				case QUOTE :
					buffer.insert( i, SLASH );
			}
		}
		buffer.insert( 0, QUOTE );
		buffer.append(    QUOTE );
		return buffer.toString();
	}

    public static boolean needsQuotes(String s) {
		return ( s.indexOf( BLANK ) > -1 ) ||
		       ( s.indexOf( QUOTE ) > -1 );
	}
    public static boolean hasQuotes(String s) {
    	return s.startsWith( "\"" ) &&
    	       s.endsWith  ( "\"" );
    }
	public static String removeQuotes(String s) {
		StringBuffer buffer = new StringBuffer();
		int          i      = 1;
		int          end    = s.length()-1;
		while (i < end) {
			char c = s.charAt( i );
			if ( c == SLASH ) {
				 c = s.charAt( ++i );
			}
			buffer.append( c );
			i++;
		}
		return buffer.toString();
	}
}
