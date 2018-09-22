package com.fintrackindia.moneymanager.utils;

import android.util.SparseBooleanArray;

import com.fintrackindia.moneymanager.database.RealmManager;
import com.fintrackindia.moneymanager.interfaces.IDateMode;
import com.fintrackindia.moneymanager.interfaces.IExpensesType;
import com.fintrackindia.moneymanager.spends.Expense;
import com.fintrackindia.moneymanager.spends.categories.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExpensesManager {

    private static ExpensesManager ourInstance = new ExpensesManager();
    private List<Expense> mExpensesList = new ArrayList<>();
    private SparseBooleanArray mSelectedExpensesItems = new SparseBooleanArray();

    private ExpensesManager() {
    }

    public static ExpensesManager getInstance() {
        return ourInstance;
    }

    public void setExpensesList(Date dateFrom, Date dateTo, @IExpensesType int type, Category category) {
        mExpensesList = Expense.getExpensesList(dateFrom, dateTo, type, category);
        resetSelectedItems();
    }

    public void setExpensesListByDateMode(@IDateMode int mCurrentDateMode) {
        switch (mCurrentDateMode) {
            case IDateMode.MODE_TODAY:
                mExpensesList = Expense.getTodayExpenses();
                break;
            case IDateMode.MODE_WEEK:
                mExpensesList = Expense.getWeekExpenses();
                break;
            case IDateMode.MODE_MONTH:
                mExpensesList = Expense.getMonthExpenses();
                break;
        }
    }

    public List<Expense> getExpensesList() {
        return mExpensesList;
    }

    public SparseBooleanArray getSelectedExpensesItems() {
        return mSelectedExpensesItems;
    }

    public void resetSelectedItems() {
        mSelectedExpensesItems.clear();
    }

    public List<Integer> getSelectedExpensesIndex() {
        List<Integer> items = new ArrayList<>(mSelectedExpensesItems.size());
        for (int i = 0; i < mSelectedExpensesItems.size(); ++i) {
            items.add(mSelectedExpensesItems.keyAt(i));
        }
        return items;
    }

    public void eraseSelectedExpenses() {
        boolean isToday = false;
        List<Expense> expensesToDelete = new ArrayList<>();
        for (int position : getSelectedExpensesIndex()) {
            Expense expense = mExpensesList.get(position);
            expensesToDelete.add(expense);
            Date expenseDate = expense.getDate();
            // update widget if the expense is created today
            if (DateUtils.isToday(expenseDate)) {
                isToday = true;
            }
        }
        /*if (isToday) {
            Intent i = new Intent(MyApplication.getContext(), ExpensesWidgetProvider.class);
            i.setAction(ExpensesWidgetService.UPDATE_WIDGET);
            EdgeExpensesApp.getContext().sendBroadcast(i);
        }*/
        RealmManager.getInstance().delete(expensesToDelete);
    }

    public void setSelectedItems(SparseBooleanArray selectedItems) {
        this.mSelectedExpensesItems = selectedItems;
    }

}
