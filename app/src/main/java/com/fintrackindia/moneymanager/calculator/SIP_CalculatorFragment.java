package com.fintrackindia.moneymanager.calculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fintrackindia.moneymanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SIP_CalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SIP_CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SIP_CalculatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public final String TAG = "LoginActivity";
    RequestQueue queue;
    TextView tv_month, tv_rate, tv_invested_amount, tv_growth_value, tv_maturity_amount, tv_amount_tooltip, tv_month_tooltip, tv_month_rate;
    EditText tv_amount;
    String sip_amount = "250000", interest_rate = "12", period = "10";
    String key = "1f72650b-2c60-4f86-8690-bf27df23a310";
    /*https://www.advisorkhoj.com/api/calc/getSIPCalcResult*/
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SIP_CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SIP_CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SIP_CalculatorFragment newInstance(String param1, String param2) {
        SIP_CalculatorFragment fragment = new SIP_CalculatorFragment();
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
        View view = inflater.inflate(R.layout.fragment_sip_calculator, container, false);

        queue = Volley.newRequestQueue(getContext());

        tv_amount_tooltip = (TextView) view.findViewById(R.id.tv_amount_tooltip);
        tv_month_tooltip = (TextView) view.findViewById(R.id.tv_month_tooltip);
        tv_month_rate = (TextView) view.findViewById(R.id.tv_month_rate);
        tv_amount = (EditText) view.findViewById(R.id.tv_amount);
        tv_month = (TextView) view.findViewById(R.id.tv_month);
        tv_rate = (TextView) view.findViewById(R.id.tv_rate);
        tv_invested_amount = (TextView) view.findViewById(R.id.tv_invested_amount);
        tv_growth_value = (TextView) view.findViewById(R.id.tv_growth_value);
        tv_maturity_amount = (TextView) view.findViewById(R.id.tv_maturity_amount);

        final SeekBar seekBar_amount = (SeekBar) view.findViewById(R.id.seekBar_amount);

        seekBar_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tv_amount.setText(String.valueOf(i));

                tv_invested_amount.setText(String.valueOf(i));
                sip_amount = String.valueOf(i);
                tv_amount_tooltip.setVisibility(View.VISIBLE);
                //Get the thumb bound and get its left value
                tv_amount_tooltip.setText(String.valueOf(i));
                int x = seekBar_amount.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_amount_tooltip.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SIPCal(sip_amount, interest_rate, period);
                tv_amount_tooltip.setVisibility(View.INVISIBLE);
            }
        });

        tv_amount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try {
                    int p = Integer.parseInt(String.valueOf(s));
                    if (p < 200000) {
                        seekBar_amount.setProgress(p);
                    } else {
                        Toast.makeText(getContext(), "Not more then 200000", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });

        final SeekBar seekBar_month = (SeekBar) view.findViewById(R.id.seekBar_month);

        int x = seekBar_month.getThumb().getBounds().left;
        //set the left value to textview x value
        tv_month_tooltip.setX(x);
        seekBar_month.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                tv_month.setText(String.valueOf(i));
                int p = i / 12;
                period = String.valueOf(p);
                tv_month_tooltip.setVisibility(View.VISIBLE);
                //Get the thumb bound and get its left value
                tv_month_tooltip.setText(String.valueOf(i));
                int x = seekBar_month.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_month_tooltip.setX(x);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SIPCal(sip_amount, interest_rate, period);
                tv_month_tooltip.setVisibility(View.INVISIBLE);
            }
        });

        final SeekBar seekBar_rate = (SeekBar) view.findViewById(R.id.seekBar_rate);

        seekBar_rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                float value = (float) ((float) i / 1.0);
                tv_rate.setText(String.valueOf(value) + " %");
                interest_rate = String.valueOf(value);
                //Get the thumb bound and get its left value
                tv_month_rate.setVisibility(View.VISIBLE);
                tv_month_rate.setText(String.valueOf(value));
                int x = seekBar_rate.getThumb().getBounds().left;
                //set the left value to textview x value
                tv_month_rate.setX(x);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SIPCal(sip_amount, interest_rate, period);
                tv_month_rate.setVisibility(View.INVISIBLE);
            }
        });

        SIPCal(sip_amount, interest_rate, period);

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

    public void SIPCal(final String sip_amount, final String interest_rate, final String period) {

        Log.d(TAG, "SIPCal Data Request:- " + "sip_amount:- " + sip_amount + " interest_rate:- " + interest_rate + " period:- " + period);

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.advisorkhoj.com/api/calc/getSIPCalcResult",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int status = 0;
                            // response
                            Log.d(TAG, "SIPCal Response:- " + response);
                           /* {"status":200,"status_msg":"Success","msg":"Success","sip_amount":10000,"interest_rate":10.0,"period":10,
                            "invested_amount":100000,"growth_value":4700,"maturity_amount":104700}*/
                            try {
                                JSONObject jsonObject_response = new JSONObject(response);
                                if (jsonObject_response.has("status"))
                                    status = jsonObject_response.getInt("status");
                                if (status == 200) {

                                }
                                tv_invested_amount.setText(jsonObject_response.getString("invested_amount"));
                                tv_growth_value.setText(jsonObject_response.getString("growth_value"));
                                tv_maturity_amount.setText(jsonObject_response.getString("maturity_amount"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (NullPointerException e) {
                            Log.d(TAG, "Response:- " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            // findViewById(R.id.progress_loader).setVisibility(View.GONE);
                            // error
                            Log.d(TAG, "Error.Response:- " + error.getMessage());
                        } catch (NullPointerException e) {
                            Log.d(TAG, "Error.Response:- " + e.getMessage());
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sip_amount", sip_amount);
                params.put("interest_rate", interest_rate);
                params.put("period", period);
                params.put("key", "1f72650b-2c60-4f86-8690-bf27df23a310");
                return params;
            }

        };
        queue.add(postRequest);

     /*   function calculateSIP() {
	sipAmount = $('#sipamount').val();
	rateofReturn = $('#sipinterest').val();
	sipMonth = $('#sipmonth').val();
	var key = "1f72650b-2c60-4f86-8690-bf27df23a310";

 	sipAmount = parseInt(sipAmount);
    sipMonth = parseInt(sipMonth);
    rateofReturn = parseFloat(rateofReturn,10);

	var invested_amount = 0;
	var growth_value = 0;
	var maturity_amount = 0;

	$.ajaxSetup({async:false});
	$.post("https://www.advisorkhoj.com/api/calc/getSIPCalcResult", {sip_amount : "" + sipAmount + "",interest_rate : "" + rateofReturn + "",period : "" + sipMonth + "",key : "" + key + ""}, function(data)
    {
		var result = $.trim(data);
		var obj = jQuery.parseJSON(result);

		if(obj.status == 400)
		{
			alert(obj.msg);
			return false;
		}

		invested_amount = obj.invested_amount;
		growth_value = obj.growth_value;
		maturity_amount = obj.maturity_amount;

    },'text');

    $('#res_amount_invest').html(numbersWithComma(invested_amount));
    $('#res_total_interest').html(numbersWithComma(growth_value));
	$('#res_total_amount').html(numbersWithComma(maturity_amount));
	renderChart(invested_amount, growth_value);

	var d = new Date();
	var current_year = d.getFullYear();
	var current_month = d.getMonth();
	barChartValuesPrepare(current_year,current_month);*/
//}
    }

    @Override
    public void onPause() {
        super.onPause();
        tv_amount_tooltip.setVisibility(View.INVISIBLE);
        tv_month_tooltip.setVisibility(View.INVISIBLE);
        tv_month_rate.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        tv_amount_tooltip.setVisibility(View.INVISIBLE);
        tv_month_tooltip.setVisibility(View.INVISIBLE);
        tv_month_rate.setVisibility(View.INVISIBLE);
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
