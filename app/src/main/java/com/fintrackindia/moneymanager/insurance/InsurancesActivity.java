package com.fintrackindia.moneymanager.insurance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.fintrackindia.moneymanager.R;
import com.fintrackindia.moneymanager.investments.Equity_Adapter;
import com.fintrackindia.moneymanager.investments.Equity_ListItem;

import java.util.ArrayList;
import java.util.List;

public class InsurancesActivity extends AppCompatActivity {
    private RecyclerView r_view_newlist;
    private Equity_Adapter equity_adapter;
    private List<Equity_ListItem> equity_listItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurances);

        r_view_newlist = (RecyclerView) findViewById(R.id.r_view_newlist);

        equity_adapter = new Equity_Adapter(InsurancesActivity.this, equity_listItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(InsurancesActivity.this);
        r_view_newlist.setLayoutManager(mLayoutManager);
        r_view_newlist.setItemAnimator(new DefaultItemAnimator());
        r_view_newlist.setAdapter(equity_adapter);
        prepareMovieData();
    }

    private void prepareMovieData() {
        /*for (int i = 0; i < 10; i++) {
            NewFixed_ListItem newFixedListItem = new NewFixed_ListItem("HDFC Ltd" + String.valueOf(i), "20000", "7.90%", "7.75%", "7.85%", "0.25");
            newFixed_listItemList.add(newFixedListItem);
        }*/


        Equity_ListItem equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_family), "Life Insurance or Personal Insurance", "5000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_medical_insurance), "Health Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_car_insurance2), "Motor Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_fire), "Fire Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_housing), "Property Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_boat), "Marine Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_rewind_time), "Liability Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_guarantee_insurance), "Guarantee Insurance", "10,000");
        equity_listItemList.add(equityListItem);

        equity_adapter.notifyDataSetChanged();
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(InsurancesActivity.this, resId);
        r_view_newlist.setLayoutAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
