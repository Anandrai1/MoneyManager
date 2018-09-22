package com.fintrackindia.moneymanager.onlie_invest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.fintrackindia.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

public class OnlieInvestActivity extends AppCompatActivity {

    private RecyclerView r_view_newlist;
    private OnlieInvestAdapter onlieInvestAdapter;
    private List<OnlineInvesterList> onlineInvesterLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlie_invest);

        r_view_newlist = (RecyclerView) findViewById(R.id.r_view_onlineinvest);

        onlieInvestAdapter = new OnlieInvestAdapter(OnlieInvestActivity.this, onlineInvesterLists);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OnlieInvestActivity.this);
        r_view_newlist.setLayoutManager(mLayoutManager);
        r_view_newlist.setItemAnimator(new DefaultItemAnimator());
        r_view_newlist.setAdapter(onlieInvestAdapter);
        prepareMovieData();
    }

    private void prepareMovieData() {
        /*for (int i = 0; i < 10; i++) {
            NewFixed_ListItem newFixedListItem = new NewFixed_ListItem("HDFC Ltd" + String.valueOf(i), "20000", "7.90%", "7.75%", "7.85%", "0.25");
            newFixed_listItemList.add(newFixedListItem);
        }*/

        OnlineInvesterList onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.invesco_online), "Invesco Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.kotak_mutual_fund), "Kotak Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.idbi_mutual_fund), "IDBI Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.invesco_online), "Invesco Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.invesco_online), "Invesco Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.invesco_online), "Invesco Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.invesco_online), "Invesco Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlineInvesterList = new OnlineInvesterList(getResources().getDrawable(R.drawable.invesco_online), "Invesco Mutual Fund", "http://www.fintrackindia.com", "anandnrai@gmail.com", "123");
        onlineInvesterLists.add(onlineInvesterList);

        onlieInvestAdapter.notifyDataSetChanged();
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(OnlieInvestActivity.this, resId);
        r_view_newlist.setLayoutAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
