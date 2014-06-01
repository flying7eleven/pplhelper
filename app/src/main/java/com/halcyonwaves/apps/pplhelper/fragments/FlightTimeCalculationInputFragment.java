package com.halcyonwaves.apps.pplhelper.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.halcyonwaves.apps.pplhelper.R;

public class FlightTimeCalculationInputFragment extends Fragment {
	private OnFragmentInteractionListener mListener;


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
		// Inflate the layout for this fragment
		return inflater.inflate( R.layout.fragment_flight_time_calculation_input, container, false );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {
			case R.id.menu_calculate_results:
				if ( this.mListener != null ) {
					this.mListener.onFragmentInteraction( 0.0f, 0.0f, 0, 0 );
				}
				break;
		}
		return super.onOptionsItemSelected( item );
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
		public void onFragmentInteraction( float hoursBeforeFlight, float hoursAfterFlight, int takeoffTimeHour, int takeoffTimeMinute );
	}
}
