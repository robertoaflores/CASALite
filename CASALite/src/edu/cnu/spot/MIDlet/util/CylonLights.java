/*
 * Copyright (c) 2006 Sun Microsystems, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 **/

package edu.cnu.spot.MIDlet.util;

import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.util.Utils;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.LEDColor;

public class CylonLights {
	private ITriColorLED[] leds;
	private LightRunner    lights;
	private LEDColor       color;

	public CylonLights(EDemoBoard demo) {
		leds   = demo.getLEDs();
		lights = null;
		color  = LEDColor.YELLOW;

		lights        = new LightRunner();
		Thread thread = new Thread( lights );
		thread.setPriority( Thread.MIN_PRIORITY );
		thread.start();
	
		resume();
	}

	public void setColor(LEDColor aColor) {
//    	System.out.println( "new color [" + aColor.toString() + "]" );

		pause();
		color = aColor;
		for (int i = EDemoBoard.LED1; i <= EDemoBoard.LED8; i++) {
			leds[ i ].setColor(color);
		}
		resume();
	}
	
	public void setOff() {
		for (int i = EDemoBoard.LED1; i <= EDemoBoard.LED8; i++) {
			leds[ i ].setRGB( 0, 0, 0 );
			leds[ i ].setOff();
		}
	}

	private void resume()  {
		// set lights on
		for (int i = EDemoBoard.LED1; i <= EDemoBoard.LED8; i++) {
			leds[ i ].setColor(color);
			leds[ i ].setOn();
		}
		lights.on = true;
	}

	public void stop() {
		lights.stop = true;
	}
	
	public void pause() {
		lights.on = false;
	}

	private class LightRunner implements Runnable {
		private static final int DELAY = 100;

		private boolean stop = false;
		private boolean on   = false;

		private int reduce( int input ) {
			return (input < 10) ? 0 : input - (input / 2);
		}

		public void run() {
			int index     = EDemoBoard.LED1;
			int increment = 1;

			while (!stop) {                 
				if (on) {
					leds[ index ].setColor( color );
					// fade colors
					for (int i = EDemoBoard.LED1 ; i <= EDemoBoard.LED8 ; i++ ) {
						int r = reduce(leds[ i ].getRed());
						int g = reduce(leds[ i ].getGreen());
						int b = reduce(leds[ i ].getBlue());
						leds[ i ].setRGB( r, g, b );
					}
					// update index
					index += increment;
					if (index < EDemoBoard.LED1 || index > EDemoBoard.LED8) {
						increment  = -increment;
						index     +=  increment;
					}
				}
				Utils.sleep( DELAY );
			}
		}
	}   
}

