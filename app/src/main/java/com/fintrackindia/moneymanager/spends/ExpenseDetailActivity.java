package com.fintrackindia.moneymanager.spends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fintrackindia.moneymanager.R;
import com.fintrackindia.moneymanager.database.RealmManager;
import com.fintrackindia.moneymanager.interfaces.IExpensesType;
import com.fintrackindia.moneymanager.utils.DateUtils;
import com.fintrackindia.moneymanager.utils.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExpenseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXPENSE_ID_KEY = "_expense_id";
    public static final int RQ_EDIT_EXPENSE = 1001;
    private String id;
    private BarChart bcWeekExpenses;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        bcWeekExpenses = (BarChart) findViewById(R.id.bc_expenses);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString(EXPENSE_ID_KEY);
            expense = (Expense) RealmManager.getInstance().findById(Expense.class, id);
            loadData();
            setWeekChart();
        }
    }

    private void loadData() {
        try {
            TextView tvExpenseTotal = ((TextView) findViewById(R.id.tv_total));
            tvExpenseTotal.setText(Util.getFormattedCurrency(expense.getTotal()));
            tvExpenseTotal.setTextColor(getResources().getColor(expense.getType() == IExpensesType.MODE_EXPENSES ? R.color.colorAccentRed : R.color.colorAccentGreen));
            ((TextView) findViewById(R.id.tv_category)).setText(String.valueOf(expense.getCategory().getName()));
            ((TextView) findViewById(R.id.tv_description)).setText(String.valueOf(expense.getDescription()));
            ((TextView) findViewById(R.id.tv_date)).setText(Util.formatDateToString(expense.getDate(), Util.getCurrentDateFormat()));
            (findViewById(R.id.fab_edit)).setOnClickListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void setWeekChart() {
        List<Date> dateList = DateUtils.getWeekDates();
        List<String> days = new ArrayList<>();
        Collections.sort(dateList);

        List<BarEntry> entriesPerDay = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {
            Date date = dateList.get(i);
            String day = Util.formatDateToString(date, "EEE");
            float value = Expense.getCategoryTotalByDate(date, expense.getCategory());
            days.add(day);
            entriesPerDay.add(new BarEntry(value, i));
        }
        BarDataSet dataSet = new BarDataSet(entriesPerDay, getString(R.string.this_week));
        // dataSet.setColors(Util.getListColors());
        BarData barData = new BarData(days, dataSet);
        bcWeekExpenses.setVisibleXRangeMaximum(5);
        bcWeekExpenses.getAxisLeft().setDrawGridLines(false);
        bcWeekExpenses.getXAxis().setDrawGridLines(false);
        bcWeekExpenses.getAxisRight().setDrawGridLines(false);
        bcWeekExpenses.getAxisRight().setDrawLabels(false);
        bcWeekExpenses.setData(barData);
        bcWeekExpenses.setDescription("");
        bcWeekExpenses.animateY(2000);
        bcWeekExpenses.invalidate();
    }
}
