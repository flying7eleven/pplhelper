package com.halcyonwaves.apps.pplhelper.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.halcyonwaves.apps.pplhelper.FlightHours;
import com.halcyonwaves.apps.pplhelper.R;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationInputFragment;
import com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationResultsFragment;


public class FlightTimeCalculationActivity extends Activity implements FlightTimeCalculationInputFragment.OnFragmentInteractionListener {
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mAppCategories;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.activity_flight_time_calculation );

		// set the default preferences
		PreferenceManager.setDefaultValues( this, R.xml.prefs, false );

		//
		this.mTitle = this.mDrawerTitle = this.getTitle();
		this.mAppCategories = getResources().getStringArray( R.array.app_categories_array );
		this.mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
		this.mDrawerList = (ListView) findViewById( R.id.left_drawer );

		// set a custom shadow that overlays the main content when the drawer opens
		this.mDrawerLayout.setDrawerShadow( R.drawable.drawer_shadow, GravityCompat.START );

		// set up the drawer's list view with items and click listener
		this.mDrawerList.setAdapter( new ArrayAdapter<String>( this, R.layout.drawer_list_item, this.mAppCategories ) );
		this.mDrawerList.setOnItemClickListener( new DrawerItemClickListener() );

		// enable ActionBar app icon to behave as action to toggle nav drawer
		this.getActionBar().setDisplayHomeAsUpEnabled( true );
		this.getActionBar().setHomeButtonEnabled( true );

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		this.mDrawerToggle = new ActionBarDrawerToggle( this, this.mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close ) {
			public void onDrawerClosed( View view ) {
				FlightTimeCalculationActivity.this.getActionBar().setTitle( FlightTimeCalculationActivity.this.mTitle );
				FlightTimeCalculationActivity.this.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened( View drawerView ) {
				FlightTimeCalculationActivity.this.getActionBar().setTitle( FlightTimeCalculationActivity.this.mDrawerTitle );
				FlightTimeCalculationActivity.this.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		this.mDrawerLayout.setDrawerListener( this.mDrawerToggle );

		// however, if we're being restored from a previous state, then we don't need to do anything and should return or else
		// we could end up with overlapping fragments
		if ( savedInstanceState == null ) {
			this.selectItem( 0 );
		}
	}

	private void selectItem( int position ) {
		// check that the activity is using the layout version with the fragment_container FrameLayout (the one-pane layout)
		if ( this.findViewById( R.id.fragment_container ) != null ) {

			// create a new Fragment to be placed in the activity layout
			FlightTimeCalculationInputFragment firstFragment = FlightTimeCalculationInputFragment.newInstance();

			// in case this activity was started with special instructions from an  Intent, pass the Intent's extras to the fragment as arguments
			firstFragment.setArguments( getIntent().getExtras() );

			// add the fragment to the 'fragment_container' FrameLayout
			this.getFragmentManager().beginTransaction().replace( R.id.fragment_container, firstFragment ).commit();
		}

		// update selected item and title, then close the drawer
		this.mDrawerList.setItemChecked( position, true );
		this.setTitle( this.mAppCategories[ position ] );
		this.mDrawerLayout.closeDrawer( mDrawerList );
	}

	@Override
	public void setTitle( CharSequence title ) {
		this.mTitle = title;
		this.getActionBar().setTitle( this.mTitle );
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate( Bundle savedInstanceState ) {
		super.onPostCreate( savedInstanceState );
		this.mDrawerToggle.syncState(); // sync. the toggle state after onRestoreInstanceState has occurred.
	}

	@Override
	public void onConfigurationChanged( Configuration newConfig ) {
		super.onConfigurationChanged( newConfig );
		this.mDrawerToggle.onConfigurationChanged( newConfig ); // pass any configuration change to the drawer toggles
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
	public void onFragmentInteraction( FlightHours hoursBeforeFlight, FlightHours hoursAfterFlight, int takeoffTimeHour, int takeoffTimeMinute ) {
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

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
			FlightTimeCalculationActivity.this.selectItem( position );
		}
	}
}
