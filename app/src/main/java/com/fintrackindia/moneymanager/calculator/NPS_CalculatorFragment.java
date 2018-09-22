package com.fintrackindia.moneymanager.calculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fintrackindia.moneymanager.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NPS_CalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NPS_CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NPS_CalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tv_current_age, tv_seekBar_current, tv_ret_age, tv_seekBar_ret, tv_seekBar_amount, tv_amount, tv_seekBar_rate, tv_rate;
    TextView tv_seekBar_month, tv_month, tv_seekBar_pwealth, tv_pwealth, tv_seekBar_erate, tv_erate;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public NPS_CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NPS_CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NPS_CalculatorFragment newInstance(String param1, String param2) {
        NPS_CalculatorFragment fragment = new NPS_CalculatorFragment();
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
        View view = inflater.inflate(R.layout.fragment_nps_calculator, container, false);

        tv_current_age = (TextView) view.findViewById(R.id.tv_current_age);
        tv_seekBar_current = (TextView) view.findViewById(R.id.tv_seekBar_current);
        tv_ret_age = (TextView) view.findViewById(R.id.tv_ret_age);
        tv_seekBar_ret = (TextView) view.findViewById(R.id.tv_seekBar_ret);
        tv_seekBar_amount = (TextView) view.findViewById(R.id.tv_seekBar_amount);
        tv_amount = (TextView) view.findViewById(R.id.tv_amount);
        tv_seekBar_rate = (TextView) view.findViewById(R.id.tv_seekBar_rate);
        tv_rate = (TextView) view.findViewById(R.id.tv_rate);
        tv_seekBar_month = (TextView) view.findViewById(R.id.tv_seekBar_month);
        tv_month = (TextView) view.findViewById(R.id.tv_month);
        tv_seekBar_pwealth = (TextView) view.findViewById(R.id.tv_seekBar_pwealth);
        tv_pwealth = (TextView) view.findViewById(R.id.tv_pwealth);
        tv_seekBar_erate = (TextView) view.findViewById(R.id.tv_seekBar_erate);
        tv_erate = (TextView) view.findViewById(R.id.tv_erate);

        final SeekBar seekBar_current = (SeekBar) view.findViewById(R.id.seekBar_current);
        seekBar_current.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tv_current_age.setText(String.valueOf(i));

                tv_seekBar_current.setVisibility(View.VISIBLE);
                //Get the thumb bound and get its left value
                tv_seekBar_current.setText(String.valueOf(i));
                int x = seekBar_current.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_current.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_current.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_ret = (SeekBar) view.findViewById(R.id.seekBar_ret);
        seekBar_ret.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tv_ret_age.setText(String.valueOf(i));
                //int p = i / 12;
                //  period = String.valueOf(p);
                tv_seekBar_ret.setVisibility(View.VISIBLE);
                //Get the thumb bound and get its left value
                tv_seekBar_ret.setText(String.valueOf(i));
                int x = seekBar_ret.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_ret.setX(x);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_ret.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_amount = (SeekBar) view.findViewById(R.id.seekBar_amount);
        seekBar_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //float value = (float) ((float) i / 1.0);
                tv_amount.setText(String.valueOf(i));
                //Get the thumb bound and get its left value
                tv_seekBar_amount.setVisibility(View.VISIBLE);
                tv_seekBar_amount.setText(String.valueOf(i));
                int x = seekBar_amount.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_amount.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_amount.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_rate = (SeekBar) view.findViewById(R.id.seekBar_rate);
        seekBar_rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //float value = (float) ((float) i / 1.0);
                tv_rate.setText(String.valueOf(i));
                //Get the thumb bound and get its left value
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

        final SeekBar seekBar_month = (SeekBar) view.findViewById(R.id.seekBar_month);
        seekBar_month.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                // float value = (float) ((float) i / 1.0);
                tv_month.setText(String.valueOf(i));
                //Get the thumb bound and get its left value
                tv_seekBar_month.setVisibility(View.VISIBLE);
                tv_seekBar_month.setText(String.valueOf(i));
                int x = seekBar_month.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_month.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_month.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_pwealth = (SeekBar) view.findViewById(R.id.seekBar_pwealth);
        seekBar_pwealth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                // float value = (float) ((float) i / 1.0);
                tv_pwealth.setText(String.valueOf(i));
                //Get the thumb bound and get its left value
                tv_seekBar_pwealth.setVisibility(View.VISIBLE);
                tv_seekBar_pwealth.setText(String.valueOf(i));
                int x = seekBar_pwealth.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_pwealth.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_pwealth.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_erate = (SeekBar) view.findViewById(R.id.seekBar_erate);
        seekBar_erate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                // float value = (float) ((float) i / 1.0);
                tv_erate.setText(String.valueOf(i));
                //Get the thumb bound and get its left value
                tv_seekBar_erate.setVisibility(View.VISIBLE);
                tv_seekBar_erate.setText(String.valueOf(i));
                int x = seekBar_erate.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_seekBar_erate.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_seekBar_erate.setVisibility(View.INVISIBLE);
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

    @Override
    public void onPause() {
        super.onPause();
        tv_seekBar_rate.setVisibility(View.INVISIBLE);
        tv_seekBar_erate.setVisibility(View.INVISIBLE);
        tv_seekBar_pwealth.setVisibility(View.INVISIBLE);
        tv_seekBar_month.setVisibility(View.INVISIBLE);
        tv_seekBar_amount.setVisibility(View.INVISIBLE);
        tv_seekBar_ret.setVisibility(View.INVISIBLE);
        tv_seekBar_current.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        tv_seekBar_rate.setVisibility(View.INVISIBLE);
        tv_seekBar_erate.setVisibility(View.INVISIBLE);
        tv_seekBar_pwealth.setVisibility(View.INVISIBLE);
        tv_seekBar_month.setVisibility(View.INVISIBLE);
        tv_seekBar_amount.setVisibility(View.INVISIBLE);
        tv_seekBar_ret.setVisibility(View.INVISIBLE);
        tv_seekBar_current.setVisibility(View.INVISIBLE);
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
