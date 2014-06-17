package com.halcyonwaves.apps.pplhelper.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.halcyonwaves.apps.pplhelper.FlightHours;
import com.halcyonwaves.apps.pplhelper.R;

import java.util.Calendar;

public class FlightTimeCalculationInputFragment extends Fragment {
	private static final String FLIGHTIME_PREVIOUS_FLIGHT_HOURS_BEFORE = "flightime.previous.flight_hours_before";
	private static final String FLIGHTIME_PREVIOUS_FLIGHT_HOURS_AFTER = "flightime.previous.flight_hours_after";
	private OnFragmentInteractionListener mListener;
	private SharedPreferences mSharedPrefs;
	private TextView mFlightHoursBefore;
	private TextView mFlightHoursAfter;
	private Button mSelectTakeoffTime;
	private int mLastHourOfTheDay;
	private int mLastMinute;

	public FlightTimeCalculationInputFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of this fragment using the provided
	 * parameters.
	 *
	 * @return A new instance of fragment {@link com.halcyonwaves.apps.pplhelper.fragments.FlightTimeCalculationInputFragment}.
	 */
	public static FlightTimeCalculationInputFragment newInstance() {
		FlightTimeCalculationInputFragment fragment = new FlightTimeCalculationInputFragment();
		Bundle args = new Bundle();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		// get the basic view of the fragment we're currently handling
		View inflatedView = inflater.inflate( R.layout.fragment_flight_time_calculation_input, container, false );

		// get some controls we need to setup their behaviour
		Button calcResults = (Button) inflatedView.findViewById( R.id.calculate_results );
		this.mSelectTakeoffTime = (Button) inflatedView.findViewById( R.id.takeoff_time );
		this.mFlightHoursBefore = (TextView) inflatedView.findViewById( R.id.editFlightHoursBrefore );
		this.mFlightHoursAfter = (TextView) inflatedView.findViewById( R.id.editFlightHoursAfter );

		// get the current time
		this.mLastHourOfTheDay = Calendar.getInstance().get( Calendar.HOUR_OF_DAY );
		this.mLastMinute = Calendar.getInstance().get( Calendar.MINUTE );

		// set the initial text for the time control
		this.mSelectTakeoffTime.setText( String.format( this.getString( R.string.current_time ), this.mLastHourOfTheDay, this.mLastMinute ) );

		// as soon as the user has entered the flight hours before, store them for later use
		this.mFlightHoursBefore.setOnFocusChangeListener( new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange( View v, boolean hasFocus ) {
				if ( !hasFocus ) {
					SharedPreferences.Editor prefEdit = FlightTimeCalculationInputFragment.this.mSharedPrefs.edit();
					prefEdit.putString( FLIGHTIME_PREVIOUS_FLIGHT_HOURS_BEFORE, FlightTimeCalculationInputFragment.this.mFlightHoursBefore.getText().toString() );
					prefEdit.commit();
				}
			}
		} );

		// as soon as the user has entered the flight hours after, store them for later use
		this.mFlightHoursAfter.setOnFocusChangeListener( new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange( View v, boolean hasFocus ) {
				if ( !hasFocus ) {
					SharedPreferences.Editor prefEdit = FlightTimeCalculationInputFragment.this.mSharedPrefs.edit();
					prefEdit.putString( FLIGHTIME_PREVIOUS_FLIGHT_HOURS_AFTER, FlightTimeCalculationInputFragment.this.mFlightHoursAfter.getText().toString() );
					prefEdit.commit();
				}
			}
		} );

		// setup the button which is used to select the takeoff time
		this.mSelectTakeoffTime.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				TimePickerDialog timePicker = new TimePickerDialog( FlightTimeCalculationInputFragment.this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
						FlightTimeCalculationInputFragment.this.mLastHourOfTheDay = hourOfDay;
						FlightTimeCalculationInputFragment.this.mLastMinute = minute;
						FlightTimeCalculationInputFragment.this.mSelectTakeoffTime.setText( String.format( FlightTimeCalculationInputFragment.this.getString( R.string.current_time ), hourOfDay, minute ) );
					}
				}, FlightTimeCalculationInputFragment.this.mLastHourOfTheDay, FlightTimeCalculationInputFragment.this.mLastMinute, true );
				timePicker.show();
			}
		} );

		// setup what should happen if the user presses the calculate button
		calcResults.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				// check if the required values are set or not
				if ( FlightTimeCalculationInputFragment.this.mFlightHoursBefore.getText().length() < 1 || FlightTimeCalculationInputFragment.this.mFlightHoursAfter.getText().length() < 1 ) {
					// TODO: show a message why we cannot proceed here
					return;
				}

				// convert the entered values to the format we want
				FlightHours flightHoursBefore = new FlightHours( FlightTimeCalculationInputFragment.this.mFlightHoursBefore.getText().toString() );
				FlightHours flightHoursAfter = new FlightHours( FlightTimeCalculationInputFragment.this.mFlightHoursAfter.getText().toString() );

				// be sure that the number of hours after the flight is higher than before
				if ( !flightHoursAfter.after( flightHoursBefore ) ) {
					// TODO: show a message why we cannot proceed here
					return;
				}

				// be sure that the keyboard is hidden before we proceed
				View currentFocus = FlightTimeCalculationInputFragment.this.getActivity().getCurrentFocus();
				if ( null != currentFocus ) {
					final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
					imm.hideSoftInputFromWindow( currentFocus.getWindowToken(), 0 );
				}

				// since all required values were set, calculate the results and show them on the next fragment
				if ( FlightTimeCalculationInputFragment.this.mListener != null ) {
					FlightTimeCalculationInputFragment.this.mListener.onFragmentInteraction( flightHoursBefore, flightHoursAfter, FlightTimeCalculationInputFragment.this.mLastHourOfTheDay, FlightTimeCalculationInputFragment.this.mLastMinute );
				}
			}
		} );

		// get the shared preferences for the application
		this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences( this.getActivity() );

		// get the values stored in the preferences
		final String flightHoursBefore = this.mSharedPrefs.getString( FLIGHTIME_PREVIOUS_FLIGHT_HOURS_BEFORE, "" );
		final String flightHoursAfter = this.mSharedPrefs.getString( FLIGHTIME_PREVIOUS_FLIGHT_HOURS_AFTER, "" );

		// just set the pre-selected values if they where set before
		if ( flightHoursBefore.length() > 0 ) {
			this.mFlightHoursBefore.setText( flightHoursBefore );
		}
		if ( flightHoursAfter.length() > 0 ) {
			this.mFlightHoursAfter.setText( flightHoursAfter );
		}

		// return the inflated view
		return inflatedView;
	}

	@Override
	public void onAttach( Activity activity ) {
		super.onAttach( activity );
		try {
			this.mListener = (OnFragmentInteractionListener) activity;
		} catch ( ClassCastException e ) {
			throw new ClassCastException( activity.toString() + " must implement OnFragmentInteractionListener" );
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		this.mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 */
	public interface OnFragmentInteractionListener {
		/**
		 * @param hoursBeforeFlight The running hours of the engine before the flight.
		 * @param hoursAfterFlight  The running ours of the engine after the flight.
		 * @param takeoffTimeHour   The hour of the takeoff.
		 * @param takeoffTimeMinute The minute of the takeoff.
		 */
		public void onFragmentInteraction( FlightHours hoursBeforeFlight, FlightHours hoursAfterFlight, int takeoffTimeHour, int takeoffTimeMinute );
	}
}
