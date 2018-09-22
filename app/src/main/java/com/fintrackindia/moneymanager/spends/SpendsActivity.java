package com.fintrackindia.moneymanager.spends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fintrackindia.moneymanager.BaseActivity;
import com.fintrackindia.moneymanager.R;
import com.fintrackindia.moneymanager.interfaces.IDateMode;
import com.fintrackindia.moneymanager.interfaces.IMainActivityListener;
import com.fintrackindia.moneymanager.spends.categories.Category;
import com.fintrackindia.moneymanager.utils.DateUtils;
import com.fintrackindia.moneymanager.utils.Util;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class SpendsActivity extends BaseActivity implements View.OnClickListener, IMainActivityListener {
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_TABS = 1;
    public static final String NAVIGATION_POSITION = "navigation_position";
    FloatingActionButton fab_addExpense;
    //private NavigationView mainNavigationView;
    BottomNavigationView navigation;
    int dateMode = IDateMode.MODE_TODAY;
    ArcProgress arcProgress;
    private int mCurrentMode = NAVIGATION_MODE_STANDARD;
    private int idSelectedNavigationItem;
    private Toolbar mToolbar;
    private TabLayout mainTabLayout;
    // Expenses Summary related views
    private LinearLayout llExpensesSummary;
    private TextView tvDate;
    // private TextView tvDescription;
    private TextView tvTotal;
    private TextView tv_AvailableAmount;
    private TextView tv_income_am;
    private TextView tv_expense_am;
    private List<Category> mCategoryList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            switchFragment(item.getItemId());
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spends);
        mCategoryList = Category.getCategoriesExpense();
        fab_addExpense = (FloatingActionButton) findViewById(R.id.fab_addExpense);
        fab_addExpense.setOnClickListener(this);
        mainTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        /*if (savedInstanceState != null) {
            int menuItemId = savedInstanceState.getInt(NAVIGATION_POSITION);
            //navigation.setCheckedItem(menuItemId);
            navigation.getMenu().performIdentifierAction(menuItemId, 0);
        } else {
            navigation.getMenu().performIdentifierAction(R.id.nav_expenses, 0);
        }*/
        llExpensesSummary = (LinearLayout) findViewById(R.id.ll_expense_container);
        tvDate = (TextView) findViewById(R.id.tv_date);
        // tvDescription = (TextView) findViewById(R.id.tv_description);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        tv_AvailableAmount = (TextView) findViewById(R.id.tv_AvailableAmount);
        tv_income_am = (TextView) findViewById(R.id.tv_income_am);
        tv_expense_am = (TextView) findViewById(R.id.tv_expense_am);
        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (!(currentFragment instanceof ExpensesContainerFragment))
            replaceFragment(ExpensesContainerFragment.newInstance(), false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(NAVIGATION_POSITION, idSelectedNavigationItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_addExpense:
                startActivity(new Intent(this, AddExpenseActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
        }
    }


    private void switchFragment(int menuItemId) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        switch (menuItemId) {
            case R.id.nav_expenses:
                if (!(currentFragment instanceof ExpensesContainerFragment))
                    replaceFragment(ExpensesContainerFragment.newInstance(), false);
                break;
           /* case R.id.nav_categories:
                if (!(currentFragment instanceof CategoriesFragment))
                    replaceFragment(CategoriesFragment.newInstance(), false);
                break;*/
            /*case R.id.nav_statistics:
                if (!(currentFragment instanceof StatisticsFragment))
                    replaceFragment(StatisticsFragment.newInstance(), false);
                break;
            case R.id.nav_reminders:
                if (!(currentFragment instanceof ReminderFragment))
                    replaceFragment(ReminderFragment.newInstance(), false);
                break;
            case R.id.nav_history:
                if (!(currentFragment instanceof HistoryFragment))
                    replaceFragment(HistoryFragment.newInstance(), false);
                break;*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        float totaltv_AvailableAmount = Expense.getAvailableAmount();
        //tv_AvailableAmount.setText("Available Amount " + Util.getFormattedCurrency(totaltv_AvailableAmount));
        tv_AvailableAmount.setText(Util.getFormattedCurrency(totaltv_AvailableAmount));

        float total = Expense.getTotalExpensesByDateMode(dateMode);
        tvTotal.setText(Util.getFormattedCurrency(total));
        float total_income = Expense.getIncomeByDateMode(dateMode);
        tv_income_am.setText(Util.getFormattedCurrency(total_income));
        float total_expense = Expense.getExpensesByDateMode(dateMode);
        tv_expense_am.setText(Util.getFormattedCurrency(total_expense));
        int expense_per = (int) ((total_expense * 100) / total_income);
        //arcProgress.setProgress((int) ((int) (total_expense * 100) / total_income));
        if (expense_per > 100) {
            arcProgress.setTextColor(getResources().getColor(R.color.red));
            arcProgress.setBottomText(expense_per + " % Expense");
            arcProgress.setProgress(100);
        } else {
            arcProgress.setTextColor(getResources().getColor(R.color.white));
            arcProgress.setBottomText("Expense as %");
            arcProgress.setProgress(expense_per);
        }
    }

    @Override
    public void setMode(int mode) {
        fab_addExpense.setVisibility(View.GONE);
        llExpensesSummary.setVisibility(View.GONE);
        mCurrentMode = mode;
        switch (mode) {
            case NAVIGATION_MODE_STANDARD:
                setNavigationModeStandard();
                break;
            case NAVIGATION_MODE_TABS:
                setNavigationModeTabs();
                break;
        }

    }

    @Override
    public void setTabs(List<String> tabList, TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mainTabLayout.removeAllTabs();
        mainTabLayout.setVisibility(View.VISIBLE);
        mainTabLayout.setOnTabSelectedListener(onTabSelectedListener);
        for (String tab : tabList) {
            mainTabLayout.addTab(mainTabLayout.newTab().setText(tab).setTag(tab));
        }
    }

    @Override
    public void setFAB(int drawableId, View.OnClickListener onClickListener) {
        fab_addExpense.setImageDrawable(getResources().getDrawable(drawableId));
        fab_addExpense.setOnClickListener(onClickListener);
        fab_addExpense.show();
    }

    @Override
    public void setTitle(String title) {
        //getSupportActionBar().setTitle(title);
    }

    @Override
    public void setPager(ViewPager vp, final TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener) {
        mainTabLayout.setupWithViewPager(vp);
        mainTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                @IDateMode int dateMode;
                switch (tab.getPosition()) {
                    case 0:
                        dateMode = IDateMode.MODE_MONTH;
                        break;
                    case 1:
                        dateMode = IDateMode.MODE_TODAY;
                        break;
                    case 2:
                        dateMode = IDateMode.MODE_WEEK;
                        break;
                    default:
                        dateMode = IDateMode.MODE_MONTH;
                }
                setExpensesSummary(dateMode);
                viewPagerOnTabSelectedListener.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPagerOnTabSelectedListener.onTabUnselected(tab);
            }
        });
        setExpensesSummary(IDateMode.MODE_MONTH);
    }

    @Override
    public void setExpensesSummary(@IDateMode int dateMode) {
        this.dateMode = dateMode;
        float totaltv_AvailableAmount = Expense.getAvailableAmount();
        tv_AvailableAmount.setText(Util.getFormattedCurrency(totaltv_AvailableAmount));

        float total = Expense.getTotalExpensesByDateMode(dateMode);
        tvTotal.setText(Util.getFormattedCurrency(total));

        float total_income = Expense.getIncomeByDateMode(dateMode);
        tv_income_am.setText(Util.getFormattedCurrency(total_income));

        float total_expense = Expense.getExpensesByDateMode(dateMode);
        tv_expense_am.setText(Util.getFormattedCurrency(total_expense));

        int expense_per = (int) ((total_expense * 100) / total_income);
        //arcProgress.setProgress((int) ((int) (total_expense * 100) / total_income));
        if (expense_per > 100) {
            arcProgress.setTextColor(getResources().getColor(R.color.red));
            arcProgress.setBottomText(expense_per + " % Expense");
            arcProgress.setProgress(100);
        } else {
            arcProgress.setTextColor(getResources().getColor(R.color.white));
            arcProgress.setBottomText("Expense as %");
            arcProgress.setProgress(expense_per);
        }

        String date;
        switch (dateMode) {
            case IDateMode.MODE_TODAY:
                date = Util.formatDateToString(DateUtils.getToday(), Util.getCurrentDateFormat());
                break;
            case IDateMode.MODE_WEEK:
                date = Util.formatDateToString(DateUtils.getFirstDateOfCurrentWeek(), Util.getCurrentDateFormat()).concat(" - ").concat(Util.formatDateToString(DateUtils.getRealLastDateOfCurrentWeek(), Util.getCurrentDateFormat()));
                break;
            case IDateMode.MODE_MONTH:
                date = Util.formatDateToString(DateUtils.getFirstDateOfCurrentMonth(), Util.getCurrentDateFormat()).concat(" - ").concat(Util.formatDateToString(DateUtils.getRealLastDateOfCurrentMonth(), Util.getCurrentDateFormat()));
                break;
            default:
                date = "";
                break;
        }
        tvDate.setText(date);
    }

    @Override
    public ActionMode setActionMode(final ActionMode.Callback actionModeCallback) {
        /*return mToolbar.startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return actionModeCallback.onCreateActionMode(mode, menu);
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return actionModeCallback.onPrepareActionMode(mode, menu);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return actionModeCallback.onActionItemClicked(mode, item);
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionModeCallback.onDestroyActionMode(mode);
            }
        });*/
        return null;
    }

    private void setNavigationModeTabs() {
        mainTabLayout.setVisibility(View.VISIBLE);
        llExpensesSummary.setVisibility(View.VISIBLE);
    }

    private void setNavigationModeStandard() {
        CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.main_coordinator);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior != null && appbar != null) {
            int[] consumed = new int[2];
            behavior.onNestedPreScroll(coordinator, appbar, null, 0, -1000, consumed);
        }
        mainTabLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_TABS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode {
    }

}
