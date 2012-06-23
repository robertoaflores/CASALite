package edu.cnu.casaLite.io;

/**
 * URLDescriptor strings are of the form: <pre>
 * scheme:// [ user @ ] [ host ] [ : port ] [ / [ [   dir  / ]* [ file ] ] [? key [ = value ] [ & key [ = value ] ]* ] ] [# fragment]
 *                                          ----directory----            --------------------query--------------------
 *                                          ---------path---------------
 * </pre> 
 * where <ul> 
 * <li> <em>scheme</em> is an alphanumeric identifier for a protocol. 
 * <li> <em>user</em> is an alphanumeric local identifier for an individual user. 
 * <li> <em>host</em> is an IP address or host name.  Default="localhost". 
 * <li> <em>port</em> is a numeric value. 
 * <li> <em>path</em> specifies a domain-specific description of an agent type. 
 * <li> <em>directory</em> is part of the path. 
 * <li> <em>file</em> is an alphanumeric string that is part of a path. 
 * <li> <em>query</em> specifies various data elements that might be used by the agent. 
 * <li> <em>key</em> is a key string (case sensitive) to retrieve the associated value. 
 * <li> <em>value</em> is an alphanumeric string. 
 * </ul>
 * For example: 
 * <blockquote> 
 * <b>agent12@www.cpsc.ucalgary.ca:8000</b> describes an agent called agent12 that is currently running and listening at port 8000<br>
 * <b>136.159.2.4/canada/alberta/calgary?lac=9000</b> describes an agent instance ("calgary") of type "canada/alberta" that may or may not be running on an unknown port, but it can be started or its port number obtained from the LAC running on port 9000. 
 * </blockquote> 
 */
public class URLDescriptor implements IURLDescriptor {
	private String scheme;   // name of the protocol
	private String user;     // name of the physical user this agent is associated with
	private String host;     // host machine url
	private int    port;     // port number on host machine
	private String path;     // agent-specific path
	private String query;    // all the key/values after the ?
	private String fragment; // the part after the #

	public URLDescriptor (int aPort) {
		setScheme  ( null ); 
		setUser    ( null );
		setHost    ( null );
		setPort    ( aPort );
		setPath    ( null );
		setFragment( null );
		setQuery   ( null );
	}
	public URLDescriptor (String aString) {
		String in = aString.trim();

		String aScheme = null;
		int posSCHEME  = in.indexOf( "://" );
		if (posSCHEME > -1) {
			aScheme = in.substring( 0, posSCHEME     );
			in      = in.substring(    posSCHEME + 3 );
		}

		String aUser = null;
		int posAT = in.indexOf( "@" );
		if (posAT > -1) {
			aUser = in.substring( 0, posAT );
			in    = in.substring(    posAT + 1 );
		}

		int from = 0;
		int posCOLON = in.indexOf( ":", from );
		if (posCOLON > -1) from = posCOLON;
		int posSLASH = in.indexOf( "/", from );
		if (posSLASH > -1) from = posSLASH;
		int posQUEST = in.indexOf( "?", from ); 
		if (posQUEST > -1) from = posQUEST;
		int posHASH  = in.indexOf( "#", from );
		if (posHASH  > -1) from = posHASH;

		String aFrag = null;
		if (posHASH > -1) {
			aFrag = in.substring(    posHASH + 1 );
			in    = in.substring( 0, posHASH );
		}

		String aQuery = null;
		if (posQUEST > -1) {
			aQuery = in.substring( posQUEST + 1 );
			in     = in.substring( 0, posQUEST );
		}

		String aPath = null;
		if (posSLASH > -1) {
			aPath = in.substring(    posSLASH + 1 );
			in    = in.substring( 0, posSLASH );
		}

		String aPort = null;
		String aHost = null;
		if (posCOLON > -1) {
			aPort = in.substring(    posCOLON + 1 );
			aHost = in.substring( 0, posCOLON );
		}
		else {
			aHost = in;
		}

		setScheme  ( aScheme );
		setUser    ( aUser );
		setHost    ( aHost );
		setPort    ( aPort );
		setPath    ( aPath );
		setQuery   ( aQuery );
		setFragment( aFrag );
	}

	public void setScheme(String aScheme) {
		scheme = (aScheme == null) ? "" : aScheme;
	}

	public void setUser(String aUser) {
		user = (aUser == null) ? "" : aUser;
		user = encode( user, false );
	}

	public void setHost(String aHost) {
		host = (aHost == null) ? "" : aHost;
	}

	public void setPort (int aPort) {
		port = aPort < 0 ? 0 : aPort;
	}
	public void setPort(String aPort) {
		try {
			if (aPort == null || aPort.equals( "" )) setPort( 0 ); 
			else                                     setPort( Integer.parseInt( aPort ));
		} 
		catch (NumberFormatException e) {
			throw new IllegalArgumentException ( NO_PORT );
		}
	}

	public void setPath (String aPath) {
		path = (aPath == null) ? "" : encode( aPath, false );
	}

	public void setQuery (String aQuery) {
		query = (aQuery == null) ? "" : aQuery;
		query = encode( query, false );
	}

	public void setFragment (String aFrag) {
		fragment = (aFrag == null) ? "" : aFrag;
		fragment = encode( fragment, false );
	}

	public String getScheme() {
		return scheme;
	}

	public String getUser() {
		return user;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPath() {
		return path;
	}

	public String getDirectory () {
		String result = "";
		if (path != null) {
			int lastSlash = path.lastIndexOf( '/' );
			if (lastSlash != -1) {
				result = path.substring( 0, lastSlash + 1 );
			}
		}
		return result;
	}

	public String getFile () {
		int lastSlash = path.lastIndexOf ('/');
		if (lastSlash == -1) {
			return path; // -1 means there is not slash: there is no directory, just a file.
		}
		return path.substring( lastSlash + 1 );
	}

	public String getQuery () {
		return query;
	}

	public String getFragment () {
		return fragment;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();

		if (scheme.length() != 0) result.append( scheme ).append( "://" );
		if (user  .length() != 0) result.append( user   ).append( "@" );

		result.append( host );
		result.append( ":" ).append( port );

		if (path    .length() != 0) result.append( "/" ).append( path );
		if (query   .length() != 0) result.append( "?" ).append( query );
		if (fragment.length() != 0) result.append( "#" ).append( fragment );

		return result.toString();
	}

	/**
	 * Given a string with "binary" data (ASCII codes less than 32), returns a string
	 * with each of the non-printable characters (less than ASCII 32) substituted
	 * with "%hh", where "h" is a hex digit.
	 * @param input the string to encode
	 * @param escapeAmps 
	 * @return the encoded string
	 */
	protected static String encode(String input, boolean escapeAmps) {
		final char   percent    = '%';
		final char   amp        = '&';
		final String ampEncoded = "&amp;";

		int          length = input.length();
		StringBuffer output = new StringBuffer();

		for (int i = 0; i < length; i++) {
			char tmp = input.charAt( i );

			if (tmp <= 0x20) {
				output.append( percent );
				if (tmp < 0x10) {
					output.append( '0' );
				}
				output.append( Integer.toHexString( tmp ));
			} 
			else {
				if (escapeAmps & tmp == amp) {
					output.append ( ampEncoded );
				} 
				else {
					output.append( tmp );
				}
			}
		}
		return output.toString();
	}
	/**
	 * Given a string with encoded "binary" data ("%hh" for ASCII codes less
	 * than 32), returns a string with each of the encoded characters replaced
	 * by the approriate "binary" (non-printable) character.
	 * @param input the string to encode
	 * @param decodeAmps
	 * @return the encoded string
	 */
	protected static String decode(String input, boolean decodeAmps) {
		final char   percent    = '%';
		final char   amp        = '&';
		final String ampEncoded = "&amp;";

		// Do the decoding
		int          length = input.length();
		StringBuffer output = new StringBuffer();

		for (int i = 0; i < length; i++) {
			char tmp = input.charAt( i );

			if (tmp == percent) {
				char testChar1 = input.charAt(i + 1);
				char testChar2 = input.charAt(i + 2);

				// If first and second character after percent are hexadecimal
				if (isHexDigit(testChar1) && isHexDigit(testChar2)) {
					// It is an encoded character
					output.append( (char) Integer.parseInt(input.substring(i + 1, i + 3), 16));
					i += 2;
				} 
				else {
					output.append(tmp);
				}
			} 
			else { 
				if (decodeAmps && tmp == amp) {
					if (input.startsWith (ampEncoded, i)) {
						output.append (amp);
						i += 4;
					}
				} 
				else {
					output.append(tmp);
				}
			}
		}
		return output.toString();
	}
	private static boolean isHexDigit(char input) {
		return (input >= '0' && input <= '9') ||
				(input >= 'a' && input <= 'f') ||
				(input >= 'A' && input <= 'F');
	}
}
