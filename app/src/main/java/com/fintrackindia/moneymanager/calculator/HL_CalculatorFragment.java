package com.fintrackindia.moneymanager.calculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fintrackindia.moneymanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HL_CalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HL_CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HL_CalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tv_year, tv_rate, tv_tloan_amount, tv_amount_tooltip, tv_seekBar_year, tv_seekBar_rate;
    EditText tv_amount;
    double loan_amount = 50000, interest_rate = 12, period = 3;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public HL_CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HL_CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HL_CalculatorFragment newInstance(String param1, String param2) {
        HL_CalculatorFragment fragment = new HL_CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hl_calculator, container, false);
        tv_amount = (EditText) view.findViewById(R.id.tv_amount);
        tv_year = (TextView) view.findViewById(R.id.tv_year);
        tv_rate = (TextView) view.findViewById(R.id.tv_rate);
        tv_tloan_amount = (TextView) view.findViewById(R.id.tv_tloan_amount);
        tv_amount_tooltip = (TextView) view.findViewById(R.id.tv_amount_tooltip);
        tv_seekBar_year = (TextView) view.findViewById(R.id.tv_seekBar_year);
        tv_seekBar_rate = (TextView) view.findViewById(R.id.tv_seekBar_rate);

        HLCalculator(loan_amount, interest_rate, period);

        final SeekBar seekBar_amount = (SeekBar) view.findViewById(R.id.seekBar_amount);

        seekBar_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tv_amount.setText(String.valueOf(i));
                loan_amount = i;
                tv_amount_tooltip.setVisibility(View.VISIBLE);
                tv_amount_tooltip.setText(String.valueOf(i));
                int x = seekBar_amount.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_amount_tooltip.setX(x);
                HLCalculator(loan_amount, interest_rate, period);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_amount_tooltip.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_year = (SeekBar) view.findViewById(R.id.seekBar_year);
        seekBar_year.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tv_seekBar_year.setVisibility(View.VISIBLE);
                tv_seekBar_year.setText(String.valueOf(i));
                int x = seekBar_year.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_year.setX(x);

                tv_year.setText(String.valueOf(i));
                period = i;
                HLCalculator(loan_amount, interest_rate, period);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_year.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_rate = (SeekBar) view.findViewById(R.id.seekBar_rate);
        seekBar_rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_rate.setText(String.valueOf(i));
                interest_rate = i;
                HLCalculator(loan_amount, interest_rate, period);

                tv_seekBar_rate.setVisibility(View.VISIBLE);
                tv_seekBar_rate.setText(String.valueOf(i));
                int x = seekBar_rate.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_rate.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_rate.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void HLCalculator(double principle, double rate, double time) {
        /* Calculate compound interest */
        int CI = (int) (principle * (Math.pow((1 + rate / 100), time)));
        tv_tloan_amount.setText(String.valueOf(CI));
    }

    @Override
    public void onPause() {
        super.onPause();
        tv_seekBar_rate.setVisibility(View.INVISIBLE);
        tv_seekBar_year.setVisibility(View.INVISIBLE);
        tv_amount_tooltip.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        tv_seekBar_rate.setVisibility(View.INVISIBLE);
        tv_seekBar_year.setVisibility(View.INVISIBLE);
        tv_amount_tooltip.setVisibility(View.INVISIBLE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
