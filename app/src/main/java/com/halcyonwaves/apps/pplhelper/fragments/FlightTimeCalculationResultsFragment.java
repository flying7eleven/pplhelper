package com.halcyonwaves.apps.pplhelper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.halcyonwaves.apps.pplhelper.R;

public class FlightTimeCalculationResultsFragment extends Fragment {
	private static final String ARG_HOURS_BEFORE_FLIGHT = "hoursBeforeFlight";
	private static final String ARG_HOURS_AFTER_FLIGHT = "hoursAfterFlight";
	private static final String ARG_TAKEOFF_TIME_HOURS = "takeoffTimeHours";
	private static final String ARG_TAKEOFF_TIME_MINUTES = "takeoffTimeMinutes";

	private float mHoursBeforeFlight;
	private float mHoursAfterFlight;
	private int mTakeoffTimeHours;
	private int mTakeoffTimeMinutes;

	public FlightTimeCalculationResultsFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param hoursBeforeFlight  TODO
	 * @param hoursAfterFlight   TODO
	 * @param takeoffTimeHours   TODO
	 * @param takeoffTimeMinutes TODO
	 * @return A new instance of fragment FlightTimeCalculationResultsFragment.
	 */
	public static FlightTimeCalculationResultsFragment newInstance( float hoursBeforeFlight, float hoursAfterFlight, int takeoffTimeHours, int takeoffTimeMinutes ) {
		FlightTimeCalculationResultsFragment fragment = new FlightTimeCalculationResultsFragment();
		Bundle args = new Bundle();
		args.putFloat( ARG_HOURS_BEFORE_FLIGHT, hoursBeforeFlight );
		args.putFloat( ARG_HOURS_AFTER_FLIGHT, hoursAfterFlight );
		args.putInt( ARG_TAKEOFF_TIME_HOURS, takeoffTimeHours );
		args.putInt( ARG_TAKEOFF_TIME_MINUTES, takeoffTimeMinutes );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		if ( this.getArguments() != null ) {
			this.mHoursBeforeFlight = getArguments().getFloat( ARG_HOURS_BEFORE_FLIGHT );
			this.mHoursAfterFlight = getArguments().getFloat( ARG_HOURS_AFTER_FLIGHT );
			this.mTakeoffTimeHours = getArguments().getInt( ARG_TAKEOFF_TIME_HOURS );
			this.mTakeoffTimeMinutes = getArguments().getInt( ARG_TAKEOFF_TIME_MINUTES );
		}
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View inflatedView = inflater.inflate( R.layout.fragment_flight_time_calculation_results, container, false );

		//
		EditText flightTimeResult = (EditText) inflatedView.findViewById( R.id.flight_time_result );

		//
		flightTimeResult.setText( "" );

		//
		return inflatedView;
	}
}
