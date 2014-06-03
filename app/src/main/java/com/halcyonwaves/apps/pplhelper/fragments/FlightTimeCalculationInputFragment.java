package com.halcyonwaves.apps.pplhelper.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.halcyonwaves.apps.pplhelper.FlightHours;
import com.halcyonwaves.apps.pplhelper.R;

import java.util.Calendar;

public class FlightTimeCalculationInputFragment extends Fragment {
	private OnFragmentInteractionListener mListener;
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

				// since all required values were set, calculate the results and show them on the next fragment
				if ( FlightTimeCalculationInputFragment.this.mListener != null ) {
					FlightTimeCalculationInputFragment.this.mListener.onFragmentInteraction( flightHoursBefore, flightHoursAfter, 0, 0 );
				}
			}
		} );

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
