package com.halcyonwaves.apps.pplhelper.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halcyonwaves.apps.pplhelper.R;

public class FlightTimeCalculationInputFragment extends Fragment {
	public FlightTimeCalculationInputFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		return inflater.inflate( R.layout.fragment_flight_time_calculation_input, container, false );
	}
}
