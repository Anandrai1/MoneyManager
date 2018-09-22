package com.fintrackindia.moneymanager.investments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.fintrackindia.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EquityInvestmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EquityInvestmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquityInvestmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView r_view_newlist;
    private Equity_Adapter equity_adapter;
    private List<Equity_ListItem> equity_listItemList = new ArrayList<>();

    public EquityInvestmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquityInvestmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EquityInvestmentFragment newInstance(String param1, String param2) {
        EquityInvestmentFragment fragment = new EquityInvestmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_equity_investment, container, false);
        r_view_newlist = (RecyclerView) view.findViewById(R.id.r_view_newlist);

        equity_adapter = new Equity_Adapter(getActivity(), equity_listItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        r_view_newlist.setLayoutManager(mLayoutManager);
        r_view_newlist.setItemAnimator(new DefaultItemAnimator());
        r_view_newlist.setAdapter(equity_adapter);
        prepareEquityData();
        return view;
    }

    private void prepareEquityData() {
        /*for (int i = 0; i < 10; i++) {
            NewFixed_ListItem newFixedListItem = new NewFixed_ListItem("HDFC Ltd" + String.valueOf(i), "20000", "7.90%", "7.75%", "7.85%", "0.25");
            newFixed_listItemList.add(newFixedListItem);
        }*/


        Equity_ListItem equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_money), "Fixed maturity plans (FMPs)", "5000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ipo), "Equity Shares", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "Debt mutual funds", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_investment), "Equity-oriented mutual fund schemes", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "RBI Taxable Bonds", "10,000");
        equity_listItemList.add(equityListItem);


        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_gold), "Gold", "10,000");
        equity_listItemList.add(equityListItem);


        equity_adapter.notifyDataSetChanged();
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        r_view_newlist.setLayoutAnimation(animation);
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
