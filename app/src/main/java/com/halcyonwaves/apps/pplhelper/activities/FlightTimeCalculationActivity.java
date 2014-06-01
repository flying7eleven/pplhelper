package com.halcyonwaves.apps.pplhelper.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.halcyonwaves.apps.pplhelper.R;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationInputFragment;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationResultsFragment;


public class FlightTimeCalculationActivity extends Activity implements FlightTimeCalculationInputFragment.OnFragmentInteractionListener {
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.activity_flight_time_calculation );

		// set the default preferences
		PreferenceManager.setDefaultValues( this, R.xml.prefs, false );

		// check that the activity is using the layout version with the fragment_container FrameLayout (the one-pane layout)
		if ( this.findViewById( R.id.fragment_container ) != null ) {

			// however, if we're being restored from a previous state, then we don't need to do anything and should return or else
			// we could end up with overlapping fragments
			if ( savedInstanceState != null ) {
				return;
			}

			// create a new Fragment to be placed in the activity layout
			FlightTimeCalculationInputFragment firstFragment = FlightTimeCalculationInputFragment.newInstance();

			// in case this activity was started with special instructions from an  Intent, pass the Intent's extras to the fragment as arguments
			firstFragment.setArguments( getIntent().getExtras() );

			// add the fragment to the 'fragment_container' FrameLayout
			this.getFragmentManager().beginTransaction().add( R.id.fragment_container, firstFragment ).commit();
		}
	}

	/**
	 * Method for receiving information which were entered in the  {@link FlightTimeCalculationInputFragment}
	 * fragment.
	 *
	 * @param hoursBeforeFlight The running hours of the engine before the flight.
	 * @param hoursAfterFlight  The running ours of the engine after the flight.
	 * @param takeoffTimeHour   The hour of the takeoff.
	 * @param takeoffTimeMinute The minute of the takeoff.
	 */
	public void onFragmentInteraction( float hoursBeforeFlight, float hoursAfterFlight, int takeoffTimeHour, int takeoffTimeMinute ) {
		FlightTimeCalculationResultsFragment articleFrag = null; //(FlightTimeCalculationResultsFragment)this.getFragmentManager().findFragmentById(R.id.flight_time_calculation_results);

		// if the fragment is available, we're in two-pane layout where both fragments are visible,
		if ( null != articleFrag ) {

		}

		// otherwise, we are in a one-pane layout and we have to bring up the new fragment
		else {
			FragmentTransaction transaction = this.getFragmentManager().beginTransaction();

			FlightTimeCalculationResultsFragment newFragment = FlightTimeCalculationResultsFragment.newInstance( hoursBeforeFlight, hoursAfterFlight, takeoffTimeHour, takeoffTimeMinute );

			transaction.replace( R.id.fragment_container, newFragment );
			transaction.addToBackStack( null );

			transaction.commit();
		}
	}

}
