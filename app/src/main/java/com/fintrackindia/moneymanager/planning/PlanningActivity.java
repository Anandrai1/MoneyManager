package com.fintrackindia.moneymanager.planning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.fintrackindia.moneymanager.R;
import com.fintrackindia.moneymanager.custom.RecyclerTouchListener;
import com.fintrackindia.moneymanager.investments.Equity_Adapter;
import com.fintrackindia.moneymanager.investments.Equity_ListItem;

import java.util.ArrayList;
import java.util.List;

public class PlanningActivity extends AppCompatActivity {

    Equity_ListItem equity_listItem;
    private RecyclerView r_view_planning;
    private Equity_Adapter equity_adapter;
    private List<Equity_ListItem> equity_listItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        r_view_planning = (RecyclerView) findViewById(R.id.r_view_planning);
        equity_adapter = new Equity_Adapter(PlanningActivity.this, equity_listItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PlanningActivity.this);
        r_view_planning.setLayoutManager(mLayoutManager);
        r_view_planning.setItemAnimator(new DefaultItemAnimator());
        r_view_planning.setAdapter(equity_adapter);
        r_view_planning.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), r_view_planning, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    Uri uri = Uri.parse("https://fintrack.omxsoft.com/Default.aspx?tabid=374816&PortalID=1&CampaignID=%7Befee527c-8979-4563-83bf-91d804ab3fe9%7D&language=en-IE&language=en-IE"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Equity_ListItem equityListItem = equity_listItemList.get(position);
                    Toast.makeText(getApplicationContext(), equityListItem.getdName() + position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareMovieData();
    }

    private void prepareMovieData() {

        /*for (int i = 0; i < 10; i++) {
            NewFixed_ListItem newFixedListItem = new NewFixed_ListItem("HDFC Ltd" + String.valueOf(i), "20000", "7.90%", "7.75%", "7.85%", "0.25");
            newFixed_listItemList.add(newFixedListItem);
        }*/

        Equity_ListItem equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "Financial Planning", "20000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "Insurance Planning", "5000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "Retirement Planning", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "Tax Planning", "10,000");
        equity_listItemList.add(equityListItem);

        equityListItem = new Equity_ListItem(getResources().getDrawable(R.drawable.ic_ecology), "Goal Planning", "10,000");
        equity_listItemList.add(equityListItem);

        equity_adapter.notifyDataSetChanged();
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(PlanningActivity.this, resId);
        r_view_planning.setLayoutAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

}
