package com.halcyonwaves.apps.pplhelper;

public class FlightHours {

	private int mFlightHours;
	private int mFlightMinutes;

	public FlightHours( int flightHours, int flightMinutes ) {
		this.mFlightHours = flightHours;
		this.mFlightMinutes = flightMinutes;
	}

	public FlightHours( String flightHours ) {
		// parse the flight hour string
		String[] parts = flightHours.split( ":" );
		if ( null == parts || parts.length < 1 || parts.length > 2 ) {
			return; // TODO: throw an exception
		}

		// set the corresponding values
		this.mFlightHours = Integer.parseInt( parts[ 0 ] );
		if ( parts.length > 1 ) {
			this.mFlightMinutes = Integer.parseInt( parts[ 1 ] );
		} else {
			this.mFlightMinutes = 0;
		}
	}

	public boolean after( FlightHours other ) {
		return true; // TODO: implement this
	}

	@Override
	public String toString() {
		return String.format( "%d:%02d", this.mFlightHours, this.mFlightMinutes );
	}
}