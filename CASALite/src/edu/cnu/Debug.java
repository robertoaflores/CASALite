package edu.cnu;

public class Debug {
	private static final boolean DEBUG = true;
	
	public static void println(String message) {
		if (DEBUG) System.out.println( message );
	}
	public static void print(String message) {
		if (DEBUG) System.out.print( message );
	}
}
