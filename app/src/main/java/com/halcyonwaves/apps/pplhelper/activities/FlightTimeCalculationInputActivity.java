package com.halcyonwaves.apps.pplhelper.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.halcyonwaves.apps.pplhelper.R;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationInputFragment;


public class FlightTimeCalculationInputActivity extends Activity {
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.activity_flight_time_calculation_input );

		// set the default preferences
		PreferenceManager.setDefaultValues( this, R.xml.prefs, false );

		// if we're not resuming from a stored state, show the fragment content of the main fragment
		if ( savedInstanceState == null ) {
			this.getFragmentManager().beginTransaction().add( R.id.container, new FlightTimeCalculationInputFragment() ).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		return super.onOptionsItemSelected( item );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		this.getMenuInflater().inflate( R.menu.menu_form_entry, menu );
		return super.onCreateOptionsMenu( menu );
	}
}
