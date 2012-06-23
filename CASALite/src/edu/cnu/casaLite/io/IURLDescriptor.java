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
public interface IURLDescriptor {
	static final String NO_PORT = "Unknown port: ";

	String getScheme();	
	String getUser();
	String getHost();
	int    getPort();
	String getPath();
	String getDirectory ();
	String getFile ();
	String getQuery ();
	String getFragment ();
}