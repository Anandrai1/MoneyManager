package com.fintrackindia.moneymanager;

import android.content.Context;

import com.fintrackindia.moneymanager.interfaces.IMainActivityListener;

public class MainFragment extends BaseFragment {
    protected IMainActivityListener mMainActivityListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivityListener = (IMainActivityListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivityListener = null;
    }

}
