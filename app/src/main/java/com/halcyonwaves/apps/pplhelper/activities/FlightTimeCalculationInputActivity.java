package com.halcyonwaves.apps.pplhelper.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.halcyonwaves.apps.pplhelper.R;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationInputFragment;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationResultsFragment;


public class FlightTimeCalculationInputActivity extends Activity implements FlightTimeCalculationInputFragment.OnFragmentInteractionListener {
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.activity_flight_time_calculation_input );

		// set the default preferences
		PreferenceManager.setDefaultValues( this, R.xml.prefs, false );
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

			FlightTimeCalculationResultsFragment newFragment = new FlightTimeCalculationResultsFragment();
			Bundle args = new Bundle();
			newFragment.setArguments( args );

			transaction.replace( R.id.fragment_container, newFragment );
			transaction.addToBackStack( null );

			transaction.commit();
		}
	}

}
