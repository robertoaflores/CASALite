package edu.cnu;

public class Debug {
	private static final boolean DEBUG = false;
	private static       int     LINE  = 1;
	
	public static void println(String message) {
		if (DEBUG) System.out.println( LINE++ + " " + message );
	}
	public static void print(String message) {
		if (DEBUG) System.out.print  ( LINE++ + " " + message );
	}
}
