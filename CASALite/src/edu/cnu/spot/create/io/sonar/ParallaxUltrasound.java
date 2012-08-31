package edu.cnu.spot.create.io.sonar;

import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.sensorboard.EDemoBoard;

public class ParallaxUltrasound {
	private final EDemoBoard demo;
	private final IIOPin     pin;

	public ParallaxUltrasound(int aPin) {
		// aPin must be in { EDemoBoard.D0..D4 }
		switch (aPin) {
		case EDemoBoard.D0:
		case EDemoBoard.D1:
		case EDemoBoard.D2:
		case EDemoBoard.D3:
		case EDemoBoard.D4:
			demo = EDemoBoard.getInstance();
			pin  = demo.getIOPins()[ aPin ];
			break;
		default:
			throw new IllegalArgumentException("aPin must be in { EDemoBoard.D0..D4 }");
		}
	}
	public double getReading() {
		pin .setAsOutput( true );               // allows +5V pulse output
		demo.startPulse ( pin, true, 20 );      // sends out +5V pulse for 20 microseconds
		pin .setAsOutput( false );              // enables as input (output = false)
		return demo.getPulse( pin, true, 500 ); // reads pin for 500 microseconds
	}
	
	public double getReadingInCentimeters() {
		double raw = getReading();
		return raw * 3 / 20;
	}
	
	public double getReadingInInches() {
		double raw = getReading();
		return raw * 3 / 51;
	}
}	
