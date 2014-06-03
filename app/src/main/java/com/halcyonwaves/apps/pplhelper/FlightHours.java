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

		// it makes no sense if the user entered a minute value bigger than 59 (it would then be an hour)
		if ( this.mFlightMinutes > 59 ) {
			return; // TODO: throw an exceptions
		}
	}

	public boolean after( FlightHours other ) {
		return true; // TODO: implement this
	}

	public FlightHours difference( FlightHours other ) {
		int newHours = this.mFlightHours - other.mFlightHours;
		int newMinutes = this.mFlightMinutes - other.mFlightMinutes;
		if ( newMinutes < 0 ) {
			newMinutes = 60 + newMinutes;
			newHours -= 1;
		}

		// be sure that the correct way of comparing the object was used
		if ( newHours < 0 ) {
			// TODO: throw an exception
		}


		// return the new flight hours object
		return new FlightHours( newHours, newMinutes );
	}

	@Override
	public String toString() {
		return String.format( "%d:%02d", this.mFlightHours, this.mFlightMinutes );
	}
}